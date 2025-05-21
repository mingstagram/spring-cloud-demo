# 🌐 Common Library - Global Exception & Response Module

`common_library`는 B2C 기반 MSA 프로젝트에서 각 서비스 간의 **예외 처리 통일성**과 **응답 포맷 일관성**을 확보하기 위해 설계된 공통 모듈입니다.

---

## ✅ 목적

- 모든 서비스에서 동일한 방식으로 예외를 처리하고,
- 공통된 `ApiResponse` 포맷으로 응답을 내려주기 위해 구성되었습니다.
- Validation, 비즈니스 예외, 시스템 예외 등을 범용적으로 처리합니다.

---

## 📦 패키지 구조 및 설명

| 파일명 | 설명 |
|--------|------|
| `ApiResponse` | 일반적인 성공/실패 응답 포맷 클래스 |
| `PageResponse` | 페이징 처리 응답 전용 DTO |
| `ValidationErrorResponse` | `@Valid` 검증 실패 시 응답 포맷 |
| `FieldErrorDetail` | 필드별 검증 오류 상세 정보 |
| `ErrorCode` | 에러 코드 및 메시지를 enum으로 정의 |
| `CustomException` | 비즈니스 예외 커스텀 클래스 |
| `GlobalExceptionHandler` | `@RestControllerAdvice`를 통한 예외 전역 처리 |

---

## 🛠 사용 예시

```java
// 비즈니스 예외 발생
if (user == null) {
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
}
```

```java
// 응답 예시
return ApiResponse.success(user);
```

---

## 📌 연동 방법

- 공통 모듈로 분리하여 각 서비스에서 dependency로 참조
- 의존성 추가 (예: `pom.xml`)

```xml
<dependency>
    <groupId>com.mingucci</groupId>
    <artifactId>common-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 💡 기대 효과

- 서비스 간 예외 및 응답 포맷 일관성 확보
- 공통 정책 적용으로 유지보수 편의성 증가
- 신규 서비스 확장 시 재사용성 극대화

---

## 🔗 관련 프로젝트
- `user-service`, `order-service`, `gateway` 등에서 공통 모듈로 사용
