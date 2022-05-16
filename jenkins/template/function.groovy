// Description : commit update 시 CI 여부를 판단하기 위해 구현한 함수로 
// git log의 첫번째 로그가 인자로 넘어온 msg가 있는지 체크 합니다.
// Return Type : Boolean ( true, false )
// Supported OS : Unix[linux]
// 테스트 안해봄
def gitFirstLogCheck(msg) {
    if (isUnix()) {
        return (sh(script: "git log -1 --pretty=%B | fgrep -ie '${msg}' -e '${msg}'", returnStatus: true) != 0)
    }
    else {
        error "not supported Agent"
    }
}