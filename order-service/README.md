# 📦 Order Service

이 서비스는 커머스 플랫폼의 **주문 처리 도메인**을 담당합니다.  
사용자의 장바구니 기반 주문 생성, 주문 내역 조회, 주문 취소 및 상태 변경 기능을 제공합니다.  
또한, **상품 재고 처리 및 실시간 알림 연동은 Kafka 기반 이벤트 발행 구조로 개선**되었습니다.

---

## 🔧 주요 기능

| 기능               | 설명                                                                 |
|--------------------|----------------------------------------------------------------------|
| 주문 생성          | 장바구니 기반으로 상품을 선택하여 주문을 생성합니다.                |
| 내 주문 내역 조회  | 로그인한 사용자의 주문 리스트를 조회합니다.                         |
| 주문 상태 변경     | 주문 상태(배송중, 완료 등)를 변경합니다.                            |
| 주문 취소          | 주문 취소 시 재고 복구 및 알림 이벤트를 발행합니다.                |
| 주문 상태 통계     | 사용자의 주문 상태별 통계를 조회합니다.                             |
| Kafka 이벤트 발행  | 주문 생성/취소 시 Kafka를 통해 다른 서비스에 이벤트 전송합니다.     |

---

## 🔌 API 목록

### 1. 주문 생성

- **URL**: `POST /order`
- **헤더**: `Authorization: Bearer {AccessToken}`
- **요청 바디 예시**:

```json
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

- **동작 요약**:
  - 재고 차감 요청: `product-service` 호출
  - Kafka 이벤트 발행: `order.created` 토픽으로 알림 전송

---

### 2. 내 주문 목록 조회

- **URL**: `GET /order/my-orders`
- **헤더**: `Authorization: Bearer {AccessToken}`

---

### 3. 주문 상태 변경

- **URL**: `PATCH /order/{orderId}/status`
- **요청 바디**:

```json
{
  "status": "DELIVERING"
}
```

---

### 4. 주문 취소

- **URL**: `PATCH /order/{orderId}/cancel`
- **헤더**: `Authorization: Bearer {AccessToken}`

- **동작 요약**:
  - 재고 복구 요청: `product-service` 호출
  - Kafka 이벤트 발행: `order.cancelled` 토픽으로 알림 전송

---

### 5. 주문 상태 통계 조회

- **URL**: `GET /order/status`
- **헤더**: `Authorization: Bearer {AccessToken}`
- **응답 예시**:

```json
{
  "ORDERED": 3,
  "DELIVERING": 1,
  "CANCELLED": 2
}
```

---

## 🛠 기술 스택

- Spring Boot 3.x
- Spring Security (JWT 인증)
- Spring Data JPA (Order 엔티티 관리)
- OpenFeign (product-service 연동)
- Kafka (이벤트 기반 메시징)
- Lombok
- Spring Validation

---

## 🤝 연동 서비스

| 서비스              | 설명                                                 |
|---------------------|------------------------------------------------------|
| **product-service** | 상품 재고 차감 및 복구 처리 (Feign 호출)            |
| **notification-service** | Kafka 기반 실시간 알림 처리 (`order.created`, `order.cancelled`) |

---

## 🧪 테스트 환경

- JWT 기반 인증 필요 (`@AuthenticationPrincipal`)
- `product-service`, `notification-service`는 Eureka 등록 필수
- 전체 요청은 Gateway를 통해 `/order/**` 경로로 라우팅됨

---

## 💡 기타 참고

- 주문 상태는 `ORDERED`, `DELIVERING`, `COMPLETED`, `CANCELLED` 등으로 관리됩니다.
- Kafka 토픽 발행은 `OrderCreatedEvent`, `OrderCancelledEvent` DTO로 이루어집니다.
- 모든 예외는 `LocalExceptionHandler`를 통해 공통 처리됩니다.

---

## 🧩 Kafka 토픽 예시

| 토픽명              | 이벤트 종류            | 주요 필드                          |
|---------------------|------------------------|------------------------------------|
| `order.created`     | 주문 생성 이벤트       | userId, orderId, 메시지, 타임스탬프 |
| `order.cancelled`   | 주문 취소 이벤트       | userId, orderId, 메시지, 타임스탬프 |
