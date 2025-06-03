# 🛍️ Product Service

이 서비스는 커머스 플랫폼의 **상품 도메인**을 담당합니다.  
상품 목록 조회, 상세 조회, 재고 차감/복구 기능을 제공합니다.  
내부 API(`/internal/**`)를 통해 주문 서비스와 연동되어 재고를 실시간으로 조정합니다.

---

## 🔧 주요 기능

| 기능             | 설명                                                            |
|------------------|-----------------------------------------------------------------|
| 상품 목록 조회    | 전체 상품 리스트를 반환합니다.                                 |
| 상품 상세 조회    | 특정 상품의 상세 정보를 조회합니다.                             |
| 재고 차감         | 주문 시 상품 재고를 차감합니다. (`/internal/{id}/decrease-stock`) |
| 재고 복구         | 주문 취소 시 상품 재고를 복구합니다. (`/internal/{id}/increase-stock`) |

---

## 🔌 API 목록

### 1. 상품 목록 조회

- **URL**: `GET /product`
- **응답 예시**:

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "productName": "샘플 상품",
      "price": 10000,
      "stock": 50
    }
  ],
  "message": null
}
```

---

### 2. 상품 상세 조회

- **URL**: `GET /product/{id}`

---

### 3. 재고 차감 (내부 호출)

- **URL**: `POST /product/internal/{id}/decrease-stock`
- **파라미터**: `quantity=3`

---

### 4. 재고 복구 (내부 호출)

- **URL**: `POST /product/internal/{id}/increase-stock`
- **파라미터**: `quantity=3`

---

## 🛠 기술 스택

- Spring Boot 3.x
- JPA (Product 엔티티 관리)
- Spring Validation
- Lombok

---

## 🤝 연동 서비스

- **order-service**
    - 주문 생성 시 → 재고 차감 호출
    - 주문 취소 시 → 재고 복구 호출

---

## 🧪 테스트 환경

- Eureka 등록 필수 (`lb://product-service`)
- Gateway를 통해 `/product/**`, `/product/internal/**` 경로로 접근
- 내부 API는 인증 없이 FeignClient를 통해 호출됨

---

## 💡 기타 참고

- 재고 부족 등 예외 상황은 `ProductNotFoundException` 및 도메인 로직에서 처리됩니다.
- 상품 조회 응답은 `ProductResponse` DTO를 통해 캡슐화되어 전달됩니다.
