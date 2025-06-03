# 🧰 Common Library

이 모듈은 커머스 플랫폼의 공통 기능을 모아놓은 **공통 유틸리티 및 설정 라이브러리**입니다.  
각 서비스는 해당 라이브러리를 의존성으로 추가하여 예외 처리, 응답 포맷, JWT 보안, Redis Pub/Sub 등을 재사용합니다.

---

## 📦 주요 패키지 구성

| 패키지 명         | 설명                                                         |
|--------------------|--------------------------------------------------------------|
| `config`           | 공통 Spring Bean 설정 (e.g. 비밀번호 인코딩)                |
| `dto`              | API 응답 포맷, 이메일 인증 요청 등 공통 DTO 정의             |
| `exception`        | 전역 예외 처리, 에러 코드 관리, 유효성 검사 에러 응답 등     |
| `jwt`              | JWT 발급 및 검증 유틸리티, 프로퍼티 설정                     |
| `redis`            | Redis Pub/Sub 구성, 메시지 발행 퍼블리셔                    |
| `security`         | JWT 인증 필터 (`JwtAuthenticationFilter`) 구현               |

---

## 🔧 예외 처리 구성

- `CustomException`, `ErrorCode`: 비즈니스 예외 및 에러코드 Enum 관리
- `GlobalExceptionHandler`: ControllerAdvice 기반 전역 예외 핸들러
- `ValidationErrorResponse`: `@Valid` 실패 시 에러 메시지 포맷 통일
- 사용 예시:

```java
if (!user.exists()) {
    throw new CustomException(ErrorCode.USER_NOT_FOUND);
}
```

---

## 🔐 JWT 유틸리티

- `JwtProvider`: AccessToken, RefreshToken 발급 및 검증 기능
- `JwtProperties`: yml 설정과 매핑되는 프로퍼티 클래스
- 서비스별 인증 필터(`JwtAuthenticationFilter`)에서 활용됨

---

## 📡 Redis Pub/Sub 구성

- `RedisConfig`: RedisTemplate 및 기본 설정
- `RedisPublisher`: 알림 메시지 발행 시 사용
- `RedisTopicConfig`: 주제 설정 (e.g. 알림용 topic)

---

## 🧪 사용 예시

**다른 서비스에서 이 라이브러리 사용 시 (Maven):**

```xml
<dependency>
    <groupId>com.minguccicommerce</groupId>
    <artifactId>common-library</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

---

## 💡 기타 참고

- 모든 공통 응답은 `ApiResponse<T>` 형태로 통일
- 모든 예외는 HTTP 상태 코드와 함께 일관된 구조로 응답됨
- 이메일 인증, JWT 인증, 알림 등 다양한 서비스 간 공통 로직 캡슐화
