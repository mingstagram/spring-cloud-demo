
# 🛍️ Product Service + Elasticsearch 연동

이 서비스는 커머스 플랫폼의 **상품 도메인**을 담당합니다.  
기본적인 상품 등록/조회/재고 관리 외에, Elasticsearch 연동을 통해 **검색 기능**도 제공합니다.

---

## 🔧 주요 기능

| 기능               | 설명                                                              |
|--------------------|-------------------------------------------------------------------|
| 상품 등록           | 상품 정보를 DB와 Elasticsearch에 모두 저장합니다.               |
| 상품 목록 조회      | 전체 상품 리스트를 반환합니다.                                   |
| 상품 상세 조회      | 특정 상품의 상세 정보를 조회합니다.                               |
| 상품 검색           | Elasticsearch 기반으로 상품명, 설명, 카테고리로 검색합니다.     |
| 재고 차감           | 주문 시 상품 재고를 차감합니다. (`/internal/{id}/decrease-stock`) |
| 재고 복구           | 주문 취소 시 상품 재고를 복구합니다. (`/internal/{id}/increase-stock`) |

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

- **URL**: `GET /product/search?query=마우스`
- **기능**: 상품명, 설명, 카테고리에 대해 부분 일치 검색 수행

---

## 📦 데이터 흐름 요약

1. 상품 등록 시:
  - DB 저장 (`productRepository.save`)
  - Elasticsearch 색인 (`productSearchRepository.save`)
2. 상품 검색 시:
  - `ProductSearchRepository`를 통한 색인 검색
3. 색인 구조:
  - `ProductDocument` 클래스 기반 (`@Document(indexName = "products")`)

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
