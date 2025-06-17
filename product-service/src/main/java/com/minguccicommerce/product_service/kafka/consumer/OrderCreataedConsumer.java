package com.minguccicommerce.product_service.kafka.consumer;

import com.minguccicommerce.common_library.dto.OrderCreatedEvent;
import com.minguccicommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreataedConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "order.created", groupId = "product-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderCreatedEvent event) {
        log.info("주문 이벤트 수신: {}", event);
        productService.decreaseStock(event.getProductId(), event.getQuantity());
    }

}
