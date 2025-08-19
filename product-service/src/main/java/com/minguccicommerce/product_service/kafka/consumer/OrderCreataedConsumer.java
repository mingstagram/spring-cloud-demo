package com.minguccicommerce.product_service.kafka.consumer;

import com.minguccicommerce.common_library.dto.OrderCreatedEvent;
import com.minguccicommerce.product_service.exception.InsufficientStockException;
import com.minguccicommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreataedConsumer {

    private final ProductService productService;

    /**
     * 주문 생성 이벤트 수신 → 재고 차감
     *
     *  참고:
     * - 동기 경로(주문요청)에서 재고를 확정하는 게 가장 안전.
     * - 비동기 소비로 차감할 경우, 반드시 "멱등성/리트라이/DLQ" 전략이 있어야 함.
     */
    @KafkaListener(
            topics = "order.created",
            groupId = "product-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderCreatedEvent event) {
        log.info("주문 이벤트 수신: {}", event);

        try {
            // 낙관적 락 + 재시도 래퍼 사용 (서비스에 이미 구현)
            productService.decreaseStockWithRetry(event.getProductId(), event.getQuantity());
            log.info("재고 차감 완료. productId={}, qty={}", event.getProductId(), event.getQuantity());

        } catch (InsufficientStockException e) {
            // 품절: 보상 트랜잭션(주문 취소/결제 취소) 트리거 필요
            log.warn("재고 부족(품절). productId={}, qty={}, err={}",
                    event.getProductId(), event.getQuantity(), e.toString());
            // rethrow 하지 않고 소비 완료 처리할지, 보상용 토픽으로 별도 발행할지 운영정책에 따름

        } catch (OptimisticLockingFailureException e) {
            // 경합 과다: 컨슈머 자동 재시도 or DLQ로 이동
            log.warn("낙관적 락 충돌 과다. 재시도 대상. productId={}, qty={}, err={}",
                    event.getProductId(), event.getQuantity(), e.toString());
            // rethrow해서 컨테이너 재시도/RetryTopic/DLQ로 넘기기
            throw e;

        } catch (Exception e) {
            // 알 수 없는 예외: DLQ로 보내고 운영에서 확인
            log.error("주문 이벤트 처리 중 알 수 없는 예외. event={}, err={}", event, e.toString(), e);
            throw e; // 컨테이너 에러핸들러(재시도/DLQ)로 위임
        }
    }

}
