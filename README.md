# 🛍️ Spring Cloud 기반 커머스 플랫폼

**Spring Cloud 기반 MSA 아키텍처를 적용한 실전형 커머스 플랫폼입니다.**  
회원가입 → 이메일 인증 → 로그인 → 상품 조회 → 장바구니 → 주문 → 결제 → 알림까지, B2C 전자상거래 흐름을 마이크로서비스로 설계하고, Redis 등 다양한 인프라와 함께 구성하였습니다.

---

## ✅ 주요 목표

- **B2C 실무 구조를 반영한 MSA 설계**
- **Spring Cloud 전체 컴포넌트 구성**
- **OpenFeign을 통한 통합 통신 구조**
- **실시간 알림, 인증 처리, 장애 대응, 트래픽 제어까지 포함한 실전 프로젝트**

---

## 🧱 프로젝트 구조

```
spring-cloud-demo/
├── gateway-service           # API 진입 지점, 인증 필터, 라우팅
├── eureka-server            # 서비스 디스커버리 서버
├── config-server (예정)     # 공통 설정 중앙화 서버
├── user-service             # 회원 도메인 (회원가입, 탈퇴, 수정, 조회)
├── auth-service             # 로그인/로그아웃, JWT 발급 및 인증
├── email-service            # 이메일 인증 코드 전송/검증 (Redis)
├── product-service          # 상품 관리 (조회, 등록, 수정), 재고 차감, 재고 조회, 상품 검색(자동완성)
├── order-service            # 주문 처리, 장바구니 흐름
├── cart-service             # Redis 기반 장바구니 관리 (상품 추가/삭제/조회)
├── payment-service          # 결제 처리 흐름 (요청, 승인, 취소)
├── notification-service     # WebSocket 실시간 알림 처리
├── monitoring-service (예정)    # 서비스 상태 모니터링 (Spring Admin)
├── common-library           # DTO, 공통 유틸, 예외 등 공유 모듈
```

---

## 🔄 사용자 흐름 기반 서비스 연동 요약

### 1️⃣ 회원가입 → 이메일 인증

- **서비스**: `user-service`, `email-service`
- **기술**: Spring Boot, Redis
- **설명**: 이메일 인증코드를 Redis에 저장 후 검증

### 2️⃣ 로그인 → JWT 발급 / 로그아웃 / 재발급

- **서비스**: `auth-service`
- **기술**: JWT, Redis, Spring Security
- **설명**: JWT Access/Refresh 발급 및 인증 필터는 Gateway에서 처리

### 3️⃣ 상품 탐색 (목록 / 상세 / 자동완성 검색)

- **서비스**: `product-service`
- **기술**: JPA, MariaDB, Elasticsearch
- **설명**: 상품 목록 및 상세 조회, Elasticsearch 기반 자동완성 구현

### 4️⃣ 장바구니 담기

- **서비스**: `cart-service`
- **기술**: Redis
- **설명**: 사용자별 Redis 기반 장바구니 저장소 구성

### 5️⃣ 주문 생성 → 재고 차감

- **서비스**: `order-service`, `product-service`
- **기술**: OpenFeign
- **설명**: 주문 요청 시 product-service에 재고 차감 요청

### 6️⃣ 결제 요청 → 승인 / 취소

- **서비스**: `payment-service`
- **기술**: TossPayments 연동, OpenFeign
- **설명**: 외부 PG 연동을 통한 결제 흐름 구현

### 7️⃣ 주문 완료 → 실시간 알림 전송

- **서비스**: `notification-service`
- **기술**: Redis Pub/Sub, WebSocket
- **설명**: 주문 완료 후 사용자에게 WebSocket 알림 전송

---

## 🧪 현재까지 진행 현황

✅ 회원가입 / 로그인 / JWT 인증 / 회원탈퇴  
✅ 이메일 인증 코드 발송 및 검증 (Redis 기반)  
✅ 상품 목록/상세 / 검색 / 등록  
✅ 주문 생성 → Product-Service에 재고 차감 요청  
✅ 주문 취소 시 → 재고 복구 처리  
✅ 결제 처리 (결제 요청 / 승인 / 취소 포함)  
✅ 장바구니 담기 (Redis 캐시 기반)  
✅ 주문 완료 시 실시간 알림  
✅ 상품 검색시 자동완성 기능

---

🟡 다음 작업 예정

☑️ 장애 발생 시 Fallback 처리 (Resilience4j)  
☑️ Gateway RateLimiter 적용  
☑️ Zipkin 기반 서비스 트레이싱

---

## ✍️ 개발자 정보

| 이름   | 깃허브                                         | 기술 블로그                                      |
| ------ | ---------------------------------------------- | ------------------------------------------------ |
| 김민국 | [@mingstagram](https://github.com/mingstagram) | [ProgramminGucci](https://mingucci.tistory.com/) |

---

## 📚 라이선스

본 프로젝트는 개인 학습 및 포트폴리오 용도로 자유롭게 사용하실 수 있습니다.
