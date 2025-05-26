package com.minguccicommerce.order_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.order_service.client.UserClient;
import com.minguccicommerce.order_service.dto.OrderRequest;
import com.minguccicommerce.order_service.dto.OrderResponse;
import com.minguccicommerce.order_service.entity.Order;
import com.minguccicommerce.order_service.repository.OrderRepository;
import com.minguccicommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> list() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(orders));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@AuthenticationPrincipal Long userId, @RequestBody @Valid OrderRequest request) {
        OrderResponse response = orderService.createOrder(userId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}