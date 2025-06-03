# 🔐 Auth Service

이 서비스는 커머스 플랫폼의 **인증 및 토큰 발급 도메인**을 담당합니다.  
로그인, 로그아웃, AccessToken 재발급 기능을 제공하며,  
JWT 발급 및 검증은 `common-library`의 `JwtProvider`를 사용합니다.

---

## 🔧 주요 기능

| 기능             | 설명                                                                 |
|------------------|----------------------------------------------------------------------|
| 로그인           | 이메일 + 비밀번호 기반 로그인, AccessToken + RefreshToken 발급      |
| 토큰 재발급      | 만료 전 RefreshToken을 사용해 AccessToken 재발급                    |
| 로그아웃         | Redis에 저장된 RefreshToken 삭제                                    |

---

## 🔌 API 목록

### 1. 로그인

- **URL**: `POST /auth/login`
- **요청 바디**:

```json
{
  "email": "test@example.com",
  "password": "1234!abcd"
}
```

- **응답 예시**:

```json
{
  "success": true,
  "data": {
    "accessToken": "xxx.yyy.zzz",
    "refreshToken": "aaa.bbb.ccc"
  },
  "message": null
}
```

---

### 2. AccessToken 재발급

- **URL**: `POST /auth/refresh`
- **헤더**: `Authorization: Bearer {RefreshToken}`

---

### 3. 로그아웃

- **URL**: `POST /auth/logout`
- **헤더**: `Authorization: Bearer {AccessToken}`

---

## 🛠 기술 스택

- Spring Boot 3.x
- Spring Security (인증 처리)
- Redis (RefreshToken 저장소)
- OpenFeign (user-service 연동)
- JWT (common-library의 JwtProvider 사용)
- Lombok

---

## 🤝 연동 서비스

- **user-service**
    - `GET /user/by-email?email=...`
    - 사용자 인증을 위한 사용자 정보 조회

---

## 🧪 테스트 환경

- AccessToken과 RefreshToken은 모두 `Authorization: Bearer {token}` 형태로 전달
- Redis 서버 설정 필요 (`refresh:{userId}` 키로 저장)
- user-service는 Eureka에 등록되어 있어야 함 (`lb://user-service`)

---

## 💡 기타 참고

- 로그인 성공 시 Redis에 RefreshToken이 저장되며, 재발급 및 로그아웃 시 이를 기준으로 처리합니다.
- 비밀번호는 `BCryptPasswordEncoder`를 통해 검증되며, 일치하지 않을 경우 예외를 발생시킵니다.
- 모든 인증 로직은 `AuthService`에서 처리되며, JWT 검증 및 발급은 `JwtProvider`를 사용합니다.
