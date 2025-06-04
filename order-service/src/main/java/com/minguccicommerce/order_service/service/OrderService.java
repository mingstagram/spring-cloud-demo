package com.minguccicommerce.order_service.service;

import com.minguccicommerce.common_library.redis.RedisPublisher;
import com.minguccicommerce.order_service.client.ProductClient;
import com.minguccicommerce.order_service.dto.OrderRequest;
import com.minguccicommerce.order_service.dto.OrderResponse;
import com.minguccicommerce.order_service.dto.OrderStatus;
import com.minguccicommerce.order_service.entity.Order;
import com.minguccicommerce.order_service.exception.OrderNotFoundException;
import com.minguccicommerce.order_service.exception.UserNotFoundException;
import com.minguccicommerce.order_service.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final RedisPublisher redisPublisher;

    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        productClient.decreaseStock(request.getProductId(), request.getQuantity());

        Order order = Order.builder()
                .userId(userId)
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(OrderStatus.CREATED)
                .build();
        orderRepository.save(order);

        // Redis로 알림 발행
        String message = String.format("주문이 완료되었습니다. (상품ID: %d, 수량: %d)", request.getProductId(), request.getQuantity());
        log.info("✅ Redis 알림 발행: {}", message); // 이거 찍히는지 로그 확인
        redisPublisher.publish(message);

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getMyOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream().map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.updateStatus(status);
    }

    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if(!order.getUserId().equals(userId)) {
            throw new UserNotFoundException("본인의 주문만 취소할 수 있습니다.");
        }

        productClient.increaseStock(order.getProductId(), order.getQuantity());
        order.cancelOrder();

    }

    public Map<OrderStatus, Long> getOrderStatus(Long userId) {
        List<Object[]> result = orderRepository.countOrdersByStatus(userId);

        return result.stream()
                .collect(Collectors.toMap(
                        row -> (OrderStatus) row[0],
                        row -> (Long) row[1]
                ));
    }

}
