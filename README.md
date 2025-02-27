# Spring 심화 주차 개인 과제

### 개요
본 프로젝트는 사용자가 프로필을 관리하고, 뉴스피드에 게시물을 작성 및 조회하며, 친구를 추가하고 상호작용할 수 있는 SNS 플랫폼입니다.

<br>

### 필수기능

#### Lv 1. 코드 개선

- **1. 코드 개선 퀴즈 - Early Return**

- **2. 리팩토링 퀴즈 - 불필요한 if-else 피하기**

- **3. 코드 개선 퀴즈 - Validation**

#### Lv 2. N+1 문제

- **TodoController와 TodoService를 통해 Todo 관련 데이터를 처리**

- **요구사항:**

  - JPQL fetch join을 사용하여 N+1 문제를 해결하고 있는 TodoRepository -> 동일한 동작을 하는 @EntityGraph 기반의 구현으로 수정

 #### Lv 3. 테스트코드 연습

- **1. 테스트 코드 연습 - 1 (예상대로 성공하는지에 대한 케이스)**

- **2. 테스트 코드 연습 - 2 (예상대로 예외처리 하는지에 대한 케이스)**

  - 1번 케이스: 테스트 패키지 package org.example.expert.domain.manager.service; 의 ManagerServiceTest 의 클래스에 있는 manager_목록_조회_시_Todo가_없다면_NPE_에러를_던진다() 테스트가 성공하고 컨텍스트와 일치하도록 테스트 코드와 테스트 코드 메서드 명 수정
 
  - 2번 케이스: 테스트 패키지 org.example.expert.domain.comment.service; 의 CommentServiceTest 의 클래스에 있는 comment_등록_중_할일을_찾지_못해_에러가_발생한다() 테스트가 성공할 수 있도록 테스트 코드를 수정
 
  - 3번 케이스: 테스트 패키지 org.example.expert.domain.manager.service의 ManagerServiceTest 클래스에 있는 todo의_user가_null인_경우_예외가_발생한다() 테스트가 성공할 수 있도록 서비스 로직을 수정

<br>

### 도전기능

#### Lv 4. API 로깅

- **키워드 : Interceptor 활용**

  - 어드민 사용자만 접근할 수 있는 특정 API에는 접근할 때마다 접근 로그를 기록
 
- **요구사항: 어드민 사용자만 접근할 수 있는 컨트롤러 메서드**

  - org.example.expert.domain.comment.controller.CommentAdminController 클래스의 deleteComment()
  - org.example.expert.domain.user.controller.UserAdminController 클래스의 changeUserRole()
 
- **로깅 구현 방법:**

  - 요청 정보(HttpServletRequest)를 사전 처리
  - 어드민 권한 여부를 확인하여 인증되지 않은 사용자의 접근을 차단
  - 인증 성공 시, 요청 시각과 URL을 로깅하도록 구현
 
- **세부 구현 가이드**

  - 어드민 인증 여부를 확인
  - 인증되지 않은 경우 예외를 발생
 
#### Lv 5. 위 제시된 기능 이외 ‘내’가 정의한 문제와 해결 과정

```js
1. [문제 인식 및 정의]

2. [해결 방안]
	2-1. [의사결정 과정]
	2-2. [해결 과정]
	
3. [해결 완료]
	3-1. [회고]
	3-2. [전후 데이터 비교]
```

- **버그나 에러 뿐만 아니라, 코드의 리팩토링 혹은 구조 개선 또한 문제에 포함**

  - 1: 코드를 꼼꼼하게 살펴보고, 개선 가능성이 있는 문제를 선정
  - 2: 문제를 정의
  - 3: 해결 과정을 기록
  - 4: 해결 후 회고를 진행해, 어떤 부분이 나아졌는지 문서로 남깁
   
Lv5 과제 답변: https://qwerdfjin.tistory.com/27
