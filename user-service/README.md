# 👤 User Service

이 서비스는 커머스 플랫폼의 **회원 관리 도메인**을 담당합니다.  
회원가입, 내 정보 조회/수정, 비밀번호 변경, 회원 탈퇴 등의 기능을 제공합니다.  
또한, 이메일 인증은 `email-service`와 연동하여 처리합니다.

---

## 🔧 주요 기능

| 기능             | 설명                                                              |
|------------------|-------------------------------------------------------------------|
| 회원가입         | 신규 회원 정보를 저장하고 이메일 인증을 진행합니다.              |
| 회원 전체 조회   | 전체 회원 목록을 조회합니다. (운영자 용도)                        |
| 회원 단건 조회   | 사용자 ID 또는 이메일로 회원 정보를 조회합니다.                  |
| 내 정보 조회     | JWT 인증을 통해 로그인한 사용자의 정보를 확인합니다.             |
| 내 정보 수정     | 사용자 정보(이름, 닉네임 등)를 수정합니다.                        |
| 비밀번호 변경    | 현재 비밀번호를 확인 후 새 비밀번호로 변경합니다.                |
| 회원 탈퇴        | 로그인한 사용자의 계정을 비활성화합니다.                         |

---

## 🔌 API 목록

### 1. 회원가입

- **URL**: `POST /user/signup`
- **요청 바디**:

```json
{
  "email": "test@example.com",
  "password": "1234!abcd",
  "name": "김민국",
  "emailCode": "028711" 
}
```
email-service에서 이메일인증이 완료된 이후에만 가능 

- **응답 예시**:

```json
{
  "success": true,
  "data": "회원가입 완료",
  "message": null
}
```

---

### 2. 회원 전체 목록 조회

- **URL**: `GET /user`

---

### 3. 사용자 ID로 회원 조회

- **URL**: `GET /user/{id}`

---

### 4. 이메일로 회원 조회

- **URL**: `GET /user/by-email?email=test@example.com`

---

### 5. 내 정보 조회

- **URL**: `GET /user/me`
- **헤더**: `Authorization: Bearer {AccessToken}`

---

### 6. 내 정보 수정

- **URL**: `PATCH /user/me`
- **요청 바디**:

```json
{
  "name": "김민순"
}
```

---

### 7. 비밀번호 변경

- **URL**: `PATCH /user/password`
- **요청 바디**:

```json
{
  "currentPassword": "1234!abcd",
  "newPassword": "new1234!abcd"
}
```

---

### 8. 회원 탈퇴

- **URL**: `DELETE /user`
- **헤더**: `Authorization: Bearer {AccessToken}`

---

## 🛠 기술 스택

- Spring Boot 3.x
- Spring Security (JWT 인증)
- JPA (User 엔티티 저장)
- OpenFeign (email-service 연동)
- Spring Validation
- Lombok

---

## 🤝 연동 서비스

- **email-service**: 이메일 인증 코드 검증
    - 경로: `POST /email/verify`
    - 연동 Client: `EmailVerifyClient`

---

## 🧪 테스트 환경

- JWT 기반 인증 필요 (`@AuthenticationPrincipal`)
- email-service는 Eureka 등록 필수 (`lb://email-service`)
- 전체 요청은 Gateway를 통해 `/user/**` 경로로 라우팅됨

---

## 💡 기타 참고

- 회원 탈퇴 시 실제 DB 삭제가 아닌 **비활성화 처리**로 동작
- 비밀번호는 `BCryptPasswordEncoder`로 암호화되어 저장됨
- 회원가입 시 이메일 중복 체크 및 인증 절차 포함
- 모든 예외는 `LocalExceptionHandler`를 통해 공통 처리됩니다.
