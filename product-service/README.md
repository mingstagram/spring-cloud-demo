# 🛍️ Product Service + Elasticsearch 연동

이 서비스는 커머스 플랫폼의 **상품 도메인**을 담당합니다.  
기본적인 상품 등록/조회/재고 관리 외에, Elasticsearch 연동을 통해 **검색 기능**, **자동완성(Auto-Complete) 기능**도 제공합니다.

---

## 🔧 주요 기능

| 기능       | 설명                                                              |
|----------|-------------------------------------------------------------------|
| 상품 등록    | 상품 정보를 DB와 Elasticsearch에 모두 저장합니다.               |
| 상품 목록 조회 | 전체 상품 리스트를 반환합니다.                                   |
| 상품 상세 조회 | 특정 상품의 상세 정보를 조회합니다.                               |
| 상품 검색    | Elasticsearch 기반으로 상품명, 설명, 카테고리로 검색합니다.     |
| 상품 자동완성  | 사용자가 입력한 prefix 기반으로 자동완성 추천어를 제공합니다.                               |
| 재고 차감    | 주문 시 상품 재고를 차감합니다. (`/internal/{id}/decrease-stock`) |
| 재고 복구    | 주문 취소 시 상품 재고를 복구합니다. (`/internal/{id}/increase-stock`) |

---

## 🔌 API 목록

### 1. 상품 등록

- **URL**: `POST /product`
- **요청 예시**:
```json
{
  "name": "무선 마우스",
  "description": "빠르고 정밀한 무선 마우스",
  "price": 25000,
  "stock": 100,
  "category": "전자기기"
}
```

### 2. 상품 검색 (Elasticsearch)

- **URL**: `POST /product/search`
- **기능**: 상품명, 설명, 카테고리에 대해 부분 일치 검색을 수행하며, 페이징 처리와 정렬을 지원합니다.
- **요청 예시**:
```json
{
  "keyword": "반팔티",
  "page": 0,
  "size": 10,
  "sortField": "id",
  "sortDirection": "asc"
}
```

- **응답 예시**:
```json
{
  "success": true,
  "data": {
    "products": [
      {
        "id": 1,
        "name": "무신사 로고 반팔티",
        "description": "베이직한 디자인의 여름용 반팔 티셔츠입니다.",
        "category": "상의",
        "price": 25000
      }
    ],
    "currentPage": 1,
    "totalPages": 2,
    "totalElements": 10
  },
  "message": null
}
```

### 3. 상품 수정 (Elasticsearch)

- **URL**: `POST /product/{id}`
- **요청 예시**:
```json
{
  "name": "여름 반팔 셔츠",
  "description": "시원하고 깔끔한 여름용 셔츠",
  "price": 39000,
  "stock": 80,
  "category": "의류"
}
```

- **응답 예시**:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "여름 반팔 셔츠",
    "description": "시원하고 깔끔한 여름용 셔츠",
    "price": 39000,
    "category": "의류"
  },
  "message": null
}
```

- **비고**:
    - `keyword`가 빈 문자열(`""`)일 경우 전체 상품을 조회합니다.
    - `page`, `size`는 기본값을 지정해둘 수 있으며, 생략 시 첫 페이지(0번), 10개 단위로 조회됩니다.

  
### 4. 상품 자동완성 (Elasticsearch)
- **URL**: `GET /product/suggest?prefix={검색어}`
- **기능**: 입력한 prefix(단어의 앞부분)에 대해 상품명 또는 카테고리 기반 자동완성 추천어를 최대 10개까지 제공합니다.
  Elasticsearch의 `Completion Suggester` 기능을 활용하여 추천어 중복 제거 및 실시간 응답을 제공합니다.


- 요청 예시:
```
GET /product/suggest?prefix=셔
```

- 응답 예시:
```json
{
  "success": true,
  "data": [
    "셔링 블라우스",
    "셔츠형 원피스",
    "셔츠형 자켓"
  ],
  "message": null
}

```

- 비고:
  - `prefix`는 사용자가 입력한 추천어의 앞글자를 의미합니다.
  - 추천어는 상품 등록 시 `ProductDocument`의 `suggest`필드에 name, category 값이 함께 색인되어 저장됩니다.
  - 색인은 상품 등록 또는 수정 시 자동으로 반영됩니다.
  - Elasticsearch 8.x + Spring Boot 3.x 환경에서 `Elasticsearch Java API Client`를 사용해 구현되었습니다.

---

## 📦 데이터 흐름 요약

### 1. 상품 등록
- DB에 저장 → Elasticsearch 색인까지 함께 등록
- 색인 시 `name`, `category` 값을 기반으로 자동완성용 Suggest 필드 구성

### 2. 상품 검색
- `keyword`로 name, description, category 필드에 대해 부분 일치 검색
- 페이징 + 정렬 포함

### 3. 자동완성
- `prefix`에 대한 `Completion Suggester` 기반 검색
- `suggest` 필드 기준으로 중복 제거된 추천어 반환

---

## 🛠 기술 스택

- Spring Boot 3.x
- JPA (Product 엔티티 관리)
- Elasticsearch (Spring Data Elasticsearch)
- Lombok
- Spring Validation

---

## ⚙️ Elasticsearch 설정 요약

- **설치 방법**: 도커 또는 윈도우 설치 가능
- **실행 시 권장 환경 변수**:
```yaml
environment:
  - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
```
- **인덱스 이름**: `products`

---

## 🧪 테스트 환경

- Eureka 등록 필수 (`lb://product-service`)
- Gateway를 통해 `/product/**`, `/product/internal/**` 경로로 접근
- 내부 API는 인증 없이 FeignClient를 통해 호출됨

---

## 💡 기타 참고

- Elasticsearch 색인이 실패하면 서비스 등록은 완료되어도 검색이 되지 않습니다.
- 검색 기능은 `ProductSearchRepository`를 통해 간단히 구현할 수 있으며, 추후 Query DSL로 확장 가능합니다.

---

## 🔗 추가 자료

- Elasticsearch 공식 문서: https://www.elastic.co/guide/en/elasticsearch/reference/index.html