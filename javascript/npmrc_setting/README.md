# Npmrc 파일 설정
NPM Publish 시에 login 하지 않고 Access Token으로 하는 법

```
.npmrc
project에 파일을 넣을 시 npm 관련 명령어에 영향 받음
Ex) yarn build ...

따라서 환경 변수로 access token을 받거나
(https://docs.npmjs.com/creating-and-viewing-access-tokens)
npmrc에 NPM_TOKEN 자리에 access token을 하드 코딩 하면 됨

CI/CD 할때 필요
```