# VS Code Lint 설정
vscode에서 auto fix 되는 eslint, prettier 적용 하기

```
1번
npm install --save-dev eslint prettier eslint-plugin-prettier eslint-config-prettier eslint-config-airbnb-base eslint-plugin-node eslint-config-node


2번 프로젝트 맞게 설정
npx eslint --init

```

이후 setting.json을 해당 프로젝트 .vscode/setting.json으로 넣기



package.json에서 build 전 prebuild 과정으로 넣고 싶으면
```
{
  "scripts": {
    "lint": "eslint .",
    "lint:fix": "eslint . --fix",
    "format": "prettier --write ."
  }
}
```
위 구문을 script쪽에 추가 하여
build 시 yarn lint 등으로 추가 


위 설정은 AirBNB 설정으로 아래 주요 사항

* 탭 대신 공백 2칸 사용
* 세미콜론 사용
* 따옴표 대신 작은 따옴표 사용
* 함수 선언 대신 화살표 함수 사용 권장
* 템플릿 리터럴 사용 권장
