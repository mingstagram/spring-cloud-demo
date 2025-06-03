# 📦 Order Service

이 서비스는 커머스 플랫폼의 **주문 처리 도메인**을 담당합니다.  
사용자의 장바구니 기반 주문 생성, 주문 내역 조회, 주문 취소 및 상태 변경 기능을 제공합니다.  
또한, 상품 재고는 `product-service`와 연동하여 실시간으로 반영됩니다.

---

## 🔧 주요 기능

| 기능               | 설명                                                                 |
|--------------------|----------------------------------------------------------------------|
| 주문 생성          | 장바구니 기반으로 상품을 선택하여 주문을 생성합니다.                |
| 내 주문 내역 조회  | 로그인한 사용자의 주문 리스트를 조회합니다.                         |
| 주문 상태 변경     | 주문 상태(배송중, 완료 등)를 변경합니다.                            |
| 주문 취소          | 주문 취소 요청 시 재고를 복구하고 주문 상태를 업데이트합니다.        |
| 주문 상태 통계     | 사용자의 주문 상태별 통계를 조회합니다.                             |

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
- JPA (Order 엔티티 관리)
- OpenFeign (product-service 연동)
- Lombok
- Spring Validation

---

## 🤝 연동 서비스

- **product-service**: 재고 차감 및 복구 API 호출
    - `POST /internal/{id}/decrease-stock`
    - `POST /internal/{id}/increase-stock`

---

## 🧪 테스트 환경

- JWT 기반 인증 필요 (`@AuthenticationPrincipal`)
- product-service는 Eureka 등록 필수 (`lb://product-service`)
- 전체 요청은 Gateway를 통해 `/order/**` 경로로 라우팅됨

---

## 💡 기타 참고

- 주문 상태는 `ORDERED`, `DELIVERING`, `COMPLETED`, `CANCELLED` 등으로 관리됩니다.
- 주문 생성 시 상품 재고 부족 또는 사용자 정보 미존재 등의 예외가 발생할 수 있습니다.
- 모든 예외는 `LocalExceptionHandler`를 통해 공통 처리됩니다.
