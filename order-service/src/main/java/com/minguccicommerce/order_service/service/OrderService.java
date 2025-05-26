package com.minguccicommerce.order_service.service;

import com.minguccicommerce.order_service.client.ProductClient;
import com.minguccicommerce.order_service.dto.OrderRequest;
import com.minguccicommerce.order_service.dto.OrderResponse;
import com.minguccicommerce.order_service.dto.OrderStatus;
import com.minguccicommerce.order_service.entity.Order;
import com.minguccicommerce.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

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

        return OrderResponse.from(order);
    }

}
