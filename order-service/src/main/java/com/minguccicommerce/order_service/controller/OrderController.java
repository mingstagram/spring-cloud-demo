package com.minguccicommerce.order_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.order_service.dto.OrderRequest;
import com.minguccicommerce.order_service.dto.OrderResponse;
import com.minguccicommerce.order_service.dto.OrderStatus;
import com.minguccicommerce.order_service.dto.OrderStatusUpdateRequest;
import com.minguccicommerce.order_service.entity.Order;
import com.minguccicommerce.order_service.repository.OrderRepository;
import com.minguccicommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>>  getMyOrders (@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.getMyOrders(userId)));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal Long userId
    ) {
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<OrderStatus, Long>>> getOrderStats(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.getOrderStatus(userId)));
    }


}