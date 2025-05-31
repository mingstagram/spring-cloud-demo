# 🛒 Cart Service

이 서비스는 사용자의 장바구니 기능을 담당합니다.  
Redis를 기반으로 사용자별 장바구니 데이터를 저장하고, 상품 추가/조회/삭제 기능을 제공합니다.

Gateway를 통해 `/cart/**` 경로로 접근할 수 있으며, 주문 서비스와 연동되어 사용됩니다.

---

## 🔧 주요 기능

| 기능          | 설명                                       |
| ------------- | ------------------------------------------ |
| 상품 추가     | Redis에 사용자별 장바구니 정보 저장        |
| 장바구니 조회 | Redis에서 사용자별 장바구니 항목 전체 조회 |
| 상품 삭제     | 장바구니에서 특정 상품 제거                |
| TTL 정책 적용 | 장바구니는 7일 동안 보관됨 (최근 사용 시 TTL 갱신 가능) |

---

## 🔌 API 목록

### 1. 장바구니에 상품 추가

- **URL**: `POST /cart`
- **헤더**: `Authorization: Bearer {AccessToken}`
- **요청 바디**:

```json
{
  "productId": 1,
  "quantity": 3
}
```

- **응답 예시**:

```json
{
  "success": true,
  "data": null,
  "message": null
}
```

- **응답 예시** (productId=500이 존재하지 않을 경우):

```json
{
  "success": false,
  "data": null,
  "message": "해당 상품을 찾을 수 없습니다. ID=500"
}
```

---

### 2. 장바구니 조회

- **URL**: `GET /cart`
- **헤더**: `Authorization: Bearer {AccessToken}`

- **응답 예시**:

```json
{
  "success": true,
  "data": {
    "1": {
      "productId": 1,
      "productName": "샘플 상품",
      "price": 10000,
      "quantity": 3
    },
    "2": {
      "productId": 2,
      "productName": "다른 상품",
      "price": 25000,
      "quantity": 1
    }
  },
  "message": null
}
```

---

### 3. 장바구니에서 상품 제거

- **URL**: `DELETE /cart/{productId}`
- **헤더**: `Authorization: Bearer {AccessToken}`

- **응답 예시**:

```json
{
  "success": true,
  "data": null,
  "message": null
}
```

---

## 🛠 기술 스택

- Spring Boot 3.x
- Redis (장바구니 저장소)
- Spring Data Redis
- Spring Web
- Spring Security (AuthenticationPrincipal)
- Lombok
- OpenFeign (ProductService 연동)

---

## 🧪 테스트 환경

- Redis 연결 필수 (e.g. `192.168.5.61:6379`)
- Product-service에 Eureka 등록 필요 (`lb://product-service` 사용)
- JWT 기반 인증 필요 (`@AuthenticationPrincipal`로 userId 추출)

---

## 💡 기타 참고

- Redis에는 `cart:{userId}` 형태로 사용자별 데이터가 저장됩니다
