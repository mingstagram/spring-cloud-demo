# 📢 Notification Service

이 서비스는 주문 완료 후 실시간 알림을 사용자에게 전송하는 역할을 합니다.  
Redis Pub/Sub과 Spring WebSocket(SockJS + STOMP)을 기반으로 구성되어 있으며,  
Gateway를 통해 `/ws-notification` 엔드포인트로 접근할 수 있습니다.

---

## 🔧 주요 기능

| 기능                  | 설명                                                              |
|-----------------------|-------------------------------------------------------------------|
| Redis 알림 구독       | Redis Pub/Sub의 `notification:channel`을 통해 메시지를 수신합니다 |
| WebSocket 알림 전송   | 수신된 알림 메시지를 `/topic/notifications` 경로로 전송합니다     |
| SockJS + STOMP 지원  | 클라이언트의 다양한 환경에서도 안정적인 WebSocket 연결을 지원합니다 |

---

## 🔌 동작 흐름

1. Order-service에서 주문 생성 성공 시 Redis에 알림 메시지를 발행합니다.
2. Notification-service는 `notification:channel`을 구독하고 메시지를 수신합니다.
3. 수신된 메시지를 WebSocket을 통해 연결된 사용자에게 실시간으로 전송합니다.

---

## 🔌 WebSocket 테스트 예제

```html
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

<script>
    const socket = new SockJS('http://localhost:8088/ws-notification');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/notifications', function (message) {
            alert('🔔 알림 수신됨: ' + message.body);
        });
    });
</script>
```

---

## 🛠 기술 스택

- Spring Boot 3.x
- Redis Pub/Sub
- Spring WebSocket (SockJS + STOMP)
- Spring Messaging
- SimpMessagingTemplate
- Lombok

---

## 🧪 테스트 환경

- Redis 연결 필수 (예: `192.168.5.61:6379`)
- Gateway가 `/ws-notification` 경로로 WebSocket 트래픽을 프록시해야 합니다
- 클라이언트는 `/topic/notifications` 구독을 통해 알림 수신

---

## 📚 기타 참고

- 클라이언트가 WebSocket 연결을 먼저 수립한 뒤에 주문을 생성해야 알림이 표시됩니다.
- Redis 채널 이름은 `notification:channel`로 고정되어 있습니다.
