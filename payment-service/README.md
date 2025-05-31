# 💳 Payment Service

이 서비스는 **Toss Payments 연동**을 통해 결제 요청 및 결제 취소를 처리합니다.  
Gateway를 통해 `/payment/**` 또는 `/toss/**` 경로로 접근할 수 있으며, 주문 생성 이후 결제 승인 및 취소 로직에 활용됩니다.

---

## 🔧 주요 기능

| 기능            | 설명                                           |
|-----------------|------------------------------------------------|
| 결제 승인 처리   | Toss 결제 성공 콜백을 받아 결제 확정 및 DB 저장 |
| 결제 취소 처리   | 사용자의 요청에 따라 Toss 결제 취소 및 DB 상태 업데이트 |

---

## 🔌 API 목록

### 1. 결제 성공 처리

- **URL**: `GET /success`
- **요청 파라미터**:
    - `paymentKey`: Toss에서 반환된 결제 키
    - `orderId`: 주문 번호 (e.g. `order-xxx`)
    - `amount`: 결제 금액

- **응답 예시**:
```json
{
  "success": true,
  "data": {
    "orderId": "order-abc123",
    "paymentKey": "tviva20250531120311Ynvg0",
    "totalAmount": 10000,
    ...
  },
  "message": null
}
```

---

### 2. 결제 취소 처리

- **URL**: `POST /toss/cancel`
- **요청 바디**:
```json
{
  "paymentKey": "tviva20250531120311Ynvg0",
  "cancelReason": "사용자 요청"
}
```

- **응답 예시**:
```json
{
  "success": true,
  "data": {
    "paymentKey": "tviva20250531120311Ynvg0",
    "orderId": "order-abc123",
    "status": "CANCELED",
    ...
  },
  "message": null
}
```

---

## 🛠 기술 스택

- Spring Boot 3.x
- WebClient (Toss API 연동)
- Spring Data JPA
- MySQL / MariaDB
- Lombok
- Spring Cloud Gateway 연동 가능

---

## 🧪 테스트 환경

- Toss Payments 테스트 키 사용 (`test_ck_...`)
- Gateway 및 Eureka 등록 필수 (`lb://payment-service` 사용)
- HTML 테스트 페이지 사용 가능 (`test.html`에서 `orderId` 직접 지정)

---

## 💡 기타 참고

- 주문 상태 연동은 추후 `order-service`와 연동 예정입니다
- 동일한 `orderId`로 중복 결제 요청 시 오류 발생 → 테스트 시 `orderId` 매번 새로 생성 필요
- 취소 시 `pgTransactionId` 기준으로 DB 상태도 `FAILED`로 자동 변경됩니다