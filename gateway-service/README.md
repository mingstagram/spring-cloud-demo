# 🚪 Gateway Service

이 서비스는 커머스 플랫폼의 **API Gateway** 역할을 담당합니다.  
각 마이크로서비스에 대한 단일 진입점을 제공하며, 라우팅 설정을 통해 요청을 전달합니다.

> 현재는 JWT 인증 필터 등 고급 기능 없이 기본 라우팅 중심의 설정만 적용되어 있습니다.

---

## 🔧 주요 기능

| 기능             | 설명                                                           |
|------------------|----------------------------------------------------------------|
| 라우팅           | 각 마이크로서비스로 요청을 전달 (Spring Cloud Gateway 활용)    |
| WebSocket 프록시 | `/ws/**` 경로를 통해 notification-service로 WebSocket 연결    |
| 정적 라우팅 테스트 | `/notification-test.html` 테스트 라우팅                        |

---

## 🌐 라우팅 정보

| 경로 Prefix        | 대상 서비스             |
|---------------------|--------------------------|
| `/user/**`          | user-service             |
| `/order/**`         | order-service            |
| `/email/**`         | email-service            |
| `/auth/**`          | auth-service             |
| `/product/**`       | product-service          |
| `/payment/**`       | payment-service          |
| `/cart/**`          | cart-service             |
| `/ws/**`            | notification-service (WebSocket) |
| `/notification-test.html` | notification-service (정적 자원) |

---

## ⚙️ 설정 요약 (application.yml)

```yaml
server:
  port: 8001

spring:
  application:
    name: gateway-service
  kafka:
    bootstrap-servers: 192.168.5.61:9092
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/user/**
          filters:
            - StripPrefix=1
            - name: UserRouteFilter
        ...
        - id: notification-service
          uri: lb:ws://notification-service
          predicates:
            - Path=/ws/**
          filters:
            - StripPrefix=1
        - id: notification-static
          uri: lb://notification-service
          predicates:
            - Path=/notification-test.html

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

logging:
  level:
    org.springframework.cloud.gateway: DEBUG
    reactor.netty.http.client: DEBUG
```

---

## 🛠 기술 스택

- Spring Boot 3.x
- Spring Cloud Gateway
- Eureka Discovery Client 
- WebFlux
- Lombok

---

## 🧪 테스트 환경

- Gateway 포트: `8001`
- 모든 서비스는 Eureka 서버를 통해 자동 라우팅 (`lb://{service-name}`)
- WebSocket 프록시는 `/ws/**` 경로에서 작동

---

## 💡 기타 참고

- 현재 인증 필터, CORS, Rate Limiting 등은 적용되어 있지 않으며 추후 확장 예정입니다.
- `/notification-test.html` 경로는 정적 리소스 테스트용으로 설정되어 있습니다.
