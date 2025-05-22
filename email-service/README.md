# 📧 Email Service

이 서비스는 사용자의 이메일로 인증 코드를 발송하고, 이를 검증하는 기능을 담당합니다.  
Gateway를 통해 `/email/**` 경로로 접근할 수 있으며, 인증 서비스(user-service) 등에서 활용됩니다.

---

## 🔧 주요 기능

| 기능 | 설명 |
|------|------|
| 이메일 인증 코드 발송 | 지정된 이메일로 6자리 인증 코드 발송 (Redis에 3분간 저장) |
| 이메일 인증 코드 검증 | 사용자로부터 입력받은 인증 코드와 Redis에 저장된 값 비교 |

---

## 🔌 API 목록

### 1. 이메일 인증 코드 발송

- **URL**: `POST /email/send`
- **요청 바디**:
```json
{
  "email": "test@naver.com"
}
```

- **응답 예시**:
```json
{
  "success": true,
  "data": "이메일 인증코드 전송 완료",
  "message": null
}
```

> 인증코드는 실제 메일 전송 대신 **로그로 출력**됩니다 (개발환경)

---

### 2. 이메일 인증 코드 검증

- **URL**: `POST /email/verify`
- **요청 바디**:
```json
{
  "email": "test@naver.com",
  "code": "916982"
}
```

- **응답 예시**:
```json
{
  "success": true,
  "data": "인증 성공",
  "message": null
}
```

---

## 🛠 기술 스택

- Spring Boot 3.x
- Redis (인증코드 저장소)
- Spring Web
- Spring Data Redis
- Lombok

---

## 🧪 테스트 환경

- 인증코드는 실제 발송 없이 **콘솔 로그로 출력**
- Redis 연결 필수 (e.g. `192.168.5.61:6379`)
- Gateway와 Eureka 등록 필수 (`lb://email-service` 사용)

---

## 💡 기타 참고

- 인증 코드 TTL은 기본 3분으로 설정되어 있습니다
- 실제 메일 발송이 필요할 경우, `JavaMailSender` 주석 해제 및 SMTP 설정 필요