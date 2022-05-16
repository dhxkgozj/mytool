BUILD_STATUS = 'SUCCESS'

def runCICheck() {
    def result = bat(returnStdout:true, script: "git log -1 --pretty=%%B | find /I \"[jenkins]\"")
    return result 
}

def getChangeFileList() {
    def changes = []
    def build = currentBuild

    while (build != null && build.result != 'SUCCESS') {
        changes += (build.changeSets.collect { changeSet ->
            (changeSet.items.collect { item ->
                (item.affectedFiles.collect { affectedFile ->
                    affectedFile.path
                }).flatten()
            }).flatten()
        }).flatten()

        build = build.previousBuild
    }

    return changes.unique()
}

pipeline {
    agent {label 'windows'}
    tools {nodejs "NodeJS16.15"}
    environment {
        NPM_TOKEN = credentials('CREDENTIALS-NPMTOKEN')
    }
    stages {
        stage('Check CI') {
            steps {
                script {
                    echo 'Check CI!!!'
                    try {
                        if (runCICheck() == "") {
                            echo 'Check Run CI...'
                            BUILD_STATUS = 'NOT_CI'
                            sh 'exit 1'
                        }
                    }
                    catch (exc) {
                        echo 'not have to working CI'
                        BUILD_STATUS = 'NOT_CI'
                        error 'not have to working CI'
                    }
                }
            }
        }
        
        stage('Yarn Install') {
            steps {
                bat 'yarn config set "strict-ssl" false'
                bat 'yarn install'
            }    
        }
        stage('Yarn Build') {
            steps {
                bat 'yarn build'
            }
        }
        stage('Deploy') {
            steps {
                bat 'yarn version --pre --minor'
                bat 'yarn publish'
                withCredentials([gitUsernamePassword(credentialsId: 'CREDENTIALS-GIT', gitToolName: 'Default')]) {
                    bat "git push origin HEAD:${env.BRANCH_NAME} --tags"
                }
            }
        }
    }
    post {
        always {
            echo 'One way or another, I have finished'
            deleteDir() /* clean up our workspace */
        }
        success {
            slackSend (channel: '#ci-cd', color: '#FFFF00', message: "[ ${env.JOB_NAME} ] Job Execute Done ${env.BUILD_ID} ")
            slackSend (channel: '#ci-cd', color: '#FFFF00', message: "build_url: ${env.BUILD_URL}")
            slackSend (channel: '#ci-cd', color: '#FFFF00', message: "ChangeFile List : ${getChangeFileList()}")
        }
        unstable {
            slackSend (channel: '#ci-cd', color: '#FFFF00', message: "Job Execute Unstable ${env.BUILD_ID}")
        }
        failure {
            script {
                if (BUILD_STATUS != 'NOT_CI') {
                    slackSend (channel: '#ci-cd', color: '#FFFF00', message: "Job Execute Failed ${env.BUILD_ID} ${BUILD_STATUS}")
                }
            }
        }
        changed {
            slackSend (channel: '#ci-cd', color: '#FFFF00', message: "Job Execute Changed")
        }
    }
}