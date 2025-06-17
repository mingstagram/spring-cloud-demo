package com.minguccicommerce.notification_service.listener;

import com.minguccicommerce.common_library.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListner {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "order.created", groupId = "notification-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("✅ Kafka 알림 수신: {}", event);

        String message = String.format("📦 주문이 완료되었습니다. (상품ID: %d, 수량: %d)",
                event.getProductId(), event.getQuantity());

        // 웹소켓으로 클라이언트에 전송 (예: /topic/notifications/{userId})
        messagingTemplate.convertAndSend("/topic/notifications/" + event.getUserId(), message);
    }

}
