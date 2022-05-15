# Debug Env Setting

```
.vscode/launch.json
내부 인자들은 아래 링크 참조
https://code.visualstudio.com/docs/editor/debugging
```

```
tsconfig.json
sourceMap을 true로 해야함. 
설정되어 있지 않으면 .ts 코드는 unbound break point 에러 발생함
단 production build에 source map이 포함되면 안됨
```


Usage
---
```
 vs code에 위파일을 셋팅 한 후
 ctrl + Shift + D
 실행 Script 선택 후 F5 Debugging 시작 