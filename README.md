# Spring User API

스프링 부트를 사용하여 만든 간단한 유저 관리 REST API입니다.  
유저의 생성, 조회, 수정, 삭제를 할 수 있으며 테스트 코드도 포함되어 있습니다.

---

## 🚀 기능 목록

- 유저 등록 (`POST /api/user3`)
- 유저 조회 (`GET /api/user/{id}`)
- 유저 전체 조회 (`GET /api/users/{id}`)
- 유저 정보 수정 (PUT/PATCH)
- 유저 삭제 (`DELETE /api/users/{id}`)
- 관리자 지정 (`PATCH /api/admin/{id}`)
- 유저 역할 확인 (`GET /api/role/{id}`)

---

## 🛠 사용 기술

- Java 17
- Spring Boot 3.5.0
- JUnit 5 + Mockito (테스트)
- Gradle
- REST API
- Lombok

---

## 💻 실행 방법

1. 프로젝트 클론

```bash
git clone https://github.com/네아이디/spring-user-api.git
cd spring-user-api
