# 🛰️ Eureka Server

이 서비스는 커머스 플랫폼의 **서비스 레지스트리(Eureka Server)** 역할을 수행합니다.  
모든 마이크로서비스는 이 Eureka 서버에 등록되며, 서로의 위치를 탐색(Discovery)하고 통신할 수 있습니다.

---

## 🛠 주요 역할

- 마이크로서비스의 등록 및 탐색 기능 제공
- 각 서비스는 `eureka-client`로 설정되어 Eureka 서버에 자신을 등록함
- Gateway 및 FeignClient는 Eureka를 통해 대상 서비스의 위치를 확인함

---

## ⚙️ 기본 설정

```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

---

## 🌐 접속 주소

- **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)

---

## 📌 참고

- 이 Eureka 서버는 자기 자신을 서비스로 등록하지 않습니다 (`register-with-eureka: false`)
- 각 마이크로서비스에서는 다음 설정을 통해 등록됩니다:

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

## 🧪 테스트 환경

- 포트: `8761` (고정)
- Gateway 및 모든 서비스는 Eureka에 의존하여 라우팅 및 연동 수행
