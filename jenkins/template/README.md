# Jenkins Pipeline Template
Devops를 하며 자주 사용 하는 pipeline template을 모아놓았습니다.  
아래 예제들은 multi pipeline 수행을 기준으로 별도의 checkout은 jenkins 설정을 통해 합니다.

```
function.groovy
pipeline에서 사용하기 위한 goovy function template
```


```
WindowsNodePublish.groovy
Windows Agent 기준 Bat 파일을 사용해 node 16.15버전 기준으로
yarn install
yarn build
yarn publish
를 수행해 NPM에 Publish 하는 예제 입니다.
```