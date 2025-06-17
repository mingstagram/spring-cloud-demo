# 🛍️ Product Service + Elasticsearch + Kafka 연동

이 서비스는 커머스 플랫폼의 **상품 도메인**을 담당합니다.  
기본적인 상품 등록/조회/재고 관리 외에, Elasticsearch 연동을 통해 **검색 기능**, **자동완성 기능**을 제공하며,  
Kafka를 통해 주문 서비스와의 **재고 차감 및 복구 메시지 처리**를 실시간으로 처리합니다.

---

## 🔧 주요 기능

| 기능           | 설명                                                                 |
|----------------|----------------------------------------------------------------------|
| 상품 등록       | 상품 정보를 DB와 Elasticsearch에 모두 저장합니다.                     |
| 상품 목록 조회   | 전체 상품 리스트를 반환합니다.                                          |
| 상품 상세 조회   | 특정 상품의 상세 정보를 조회합니다.                                      |
| 상품 검색       | Elasticsearch 기반으로 상품명, 설명, 카테고리로 검색합니다.            |
| 상품 자동완성    | 사용자가 입력한 prefix 기반으로 자동완성 추천어를 제공합니다.              |
| 재고 차감 (Kafka) | Kafka 토픽으로부터 주문 메시지를 수신하여 재고를 차감합니다.             |
| 재고 복구 (Kafka) | Kafka 토픽으로부터 취소 메시지를 수신하여 재고를 복구합니다.             |

---

## 🔄 Kafka 연동 배경

- Redis Pub/Sub은 간편하지만 확장성에 한계가 있으며, 메시지 보존/재처리/모니터링에 취약합니다.
- Kafka는 **대용량 메시지 처리**, **비동기 확장성**, **컨슈머 그룹 기반의 스케일 아웃**, **내구성 보장** 등의 이유로 MSA 구조에 적합합니다.
- Notification-service, Product-service 등 실시간 메시지 처리가 필요한 서비스에서 Kafka 기반으로 전환하였습니다.

---

## 📦 Kafka 메시지 처리 흐름

1. Order-service에서 주문 생성/취소 시 Kafka 토픽(`order-topic`)에 메시지를 발행합니다.
2. Product-service는 해당 토픽을 구독하여 `재고 차감/복구` 처리를 수행합니다.
3. 메시지 형식은 아래와 같이 통일된 DTO 구조를 따릅니다:

```json
{
  "orderId": 123,
  "productId": 456,
  "quantity": 2,
  "type": "DECREASE" // 또는 "INCREASE"
}
```

---

## 🔌 Kafka 설정 요약

- **토픽 이름**: `order-topic`
- **컨슈머 그룹**: `product-group`
- **메시지 직렬화**: JSON
- **사용 라이브러리**: `spring-kafka`, `jackson-databind`

---

## 🛠 기술 스택

- Spring Boot 3.x
- Spring Kafka
- Elasticsearch (Java API Client)
- JPA (Hibernate)
- Lombok
- Spring Validation

---

## 🧪 테스트 환경

- Kafka 브로커 필수 (`localhost:9092` 또는 Docker 네트워크)
- Eureka 등록 후 Gateway를 통해 접근 (`/product/**`, `/product/internal/**`)
- Kafka 테스트 메시지는 `kafka-console-producer` 또는 Postman으로 전송 가능

---

## 🔗 추가 자료

- 실습 예제 및 소스코드: [GitHub - mingstagram/spring-cloud-demo](https://github.com/mingstagram/spring-cloud-demo)
- Kafka 공식 문서: https://kafka.apache.org/documentation/

