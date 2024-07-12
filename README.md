# FreshMan-Backend

### Project Rule

- PR에 코드 리뷰를 답니다.
  - 개선하면 좋을 것 같은 사항이 있으면 서로의 성장을 위해 반드시 지적해줍니다.
  - 궁금한 부분도 물어봅니다.
- 클린한 코드를 만듭니다.
- 커밋 단위를 신경씁니다.

### Branch 전략

- git-flow 전략을 사용합니다
  - 브랜치 네이밍
    - feature : feature/{기능 요약} 
    - release : release/{버전} 
    - hotfix : hotfix-{버전}

### 커밋 컨벤션
 
- [TYPE] 커밋메시지

### PR 컨벤션

- [TYPE] 커밋들을 포괄하는 메시지

### 민감정보의 보관

- application-secret.yml에 보관한다
- application-secret의 수정사항이 생기면 디스코드에 알리고, 노션에 내용을 업데이트한다.