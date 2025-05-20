# 🛍️ Spring Cloud 기반 커머스 플랫폼 

**Spring Cloud 기반 MSA 아키텍처를 적용한 실전형 커머스 플랫폼입니다.**  
회원가입 → 상품 조회 → 장바구니 → 주문 → 결제 → 알림까지, B2C 전자상거래 흐름을 마이크로서비스로 설계하고, Kafka, Redis 등 다양한 인프라와 함께 구성하였습니다.

---

## ✅ 주요 목표

- **B2C 실무 구조를 반영한 MSA 설계**
- **Spring Cloud 전체 컴포넌트 구성(Config, Gateway, Eureka 등)**
- **OpenFeign + Kafka를 통한 통합 통신 구조**
- **실시간 알림, 인증 처리, 장애 대응, 트래픽 제어까지 포함한 실전 프로젝트**

---

## 🧱 프로젝트 구조

```
spring-cloud-demo/
├── gateway-service           # API 진입 지점, 인증 필터, 라우팅
├── eureka-server            # 서비스 디스커버리 서버
├── config-server            # 공통 설정 중앙화 서버
├── user-service             # 회원 도메인 (회원가입, 조회)
├── auth-service             # 로그인/로그아웃, JWT 발급 및 인증
├── product-service          # 상품 관리 (조회, 등록, 수정)
├── order-service            # 주문 처리, 장바구니 흐름
├── inventory-service        # 재고 차감, 재고 조회
├── payment-service          # 결제 처리 흐름
├── email-service            # Kafka 이벤트 기반 이메일 전송
├── notification-service     # WebSocket 실시간 알림 처리
├── common-library           # DTO, 공통 유틸, 예외 등 공유 모듈
```

---

## 🔧 사용 기술

| 영역 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 3.2.x, Spring Cloud 2023.x |
| MSA 통신 | Eureka, Spring Cloud Gateway, OpenFeign, Resilience4j |
| 인증/보안 | Spring Security, JWT, Redis (토큰 관리) |
| 비동기 처리 | Kafka, Redis Pub/Sub |
| 데이터베이스 | MySQL, Redis |
| 검색 | Elasticsearch |
| 모니터링 | Spring Boot Actuator, Spring Admin, Zipkin |
| 배포 자동화 | Docker, Docker Compose (CI/CD는 Jenkins 예정) |

---

## 🧪 주요 기능

- ✅ 회원가입 / 로그인 / JWT 인증
- ✅ 상품 목록/상세 / 검색 / 등록
- ✅ 장바구니 담기 (Redis 캐시 기반)
- ✅ 주문 생성 → Kafka로 재고 차감 이벤트 발송
- ✅ 결제 처리 (모의 결제)
- ✅ 주문 완료 시 이메일 전송, 실시간 알림
- ✅ 장애 발생 시 Fallback 처리 (Resilience4j)
- ✅ Gateway RateLimiter 적용
- ✅ Zipkin 기반 서비스 트레이싱

---

## 🚀 실행 방법

### 1. 필수 실행 순서

1. `eureka-server`  
2. `config-server`  
3. `Kafka`, `Redis`, `MySQL` 등 인프라 기동  
4. 나머지 서비스 (user, auth, product 등) 개별 실행

### 2. 환경 구성 (Docker)

```bash
# Kafka + Zookeeper 실행
docker run -d --name zookeeper -p 2181:2181 bitnami/zookeeper
docker run -d --name kafka -p 9092:9092 \
  -e KAFKA_CFG_ZOOKEEPER_CONNECT=host.docker.internal:2181 \
  -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  bitnami/kafka

# Redis 실행
docker run -d -p 6379:6379 --name redis redis

# MySQL 실행
docker run -d -p 3306:3306 --name mysql \
  -e MYSQL_ROOT_PASSWORD=1234 \
  -e MYSQL_DATABASE=commerce \
  mysql:8.0

# Elasticsearch 실행
docker run -d -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  --name elasticsearch elasticsearch:8.12.0
```

---

## 🗂️ API 명세 (일부 예시)

### 🔐 [Auth Service]

```
POST /auth/login
POST /auth/logout
POST /auth/reissue
```

### 👤 [User Service]

```
GET /user/{id}
POST /user/register
```

### 🛍️ [Product Service]

```
GET /products
POST /products
```

### 🛒 [Order Service]

```
POST /orders
GET /orders/{userId}
```

---

## 🧩 프로젝트 진행 흐름

- [x] MSA 기본 구조 구성 (Eureka + Gateway)
- [x] 주문 → 사용자 조회 (Feign + Fallback)
- [ ] JWT 인증 구조 설계
- [ ] Kafka 기반 재고 처리
- [ ] 실시간 알림 및 이메일 전송
- [ ] Redis 기반 캐싱 및 트래픽 제어
- [ ] Elasticsearch 상품 검색 연동
- [ ] 모니터링 및 운영 구조 구성
- [ ] CI/CD 자동화 및 무중단 배포 구조 추가 예정

---

## 📌 기타

- 설정 파일 위치: [`spring-cloud-demo-config`](https://github.com/your-id/spring-cloud-demo-config)
- GitHub Actions or Jenkins CI/CD 적용 예정
- 실습용 Postman API 파일은 `/docs/postman_collection.json` 참고

---

## ✍️ 개발자 정보

| 이름 | 깃허브 | 기술 블로그 |
|------|--------|-------------|
| 김민국 | [@mingstagram](https://github.com/mingstagram) | [ProgramminGucci](https://mingucci.tistory.com/) |

---

## 📚 라이선스

본 프로젝트는 개인 학습 및 포트폴리오 용도로 자유롭게 사용하실 수 있습니다.
