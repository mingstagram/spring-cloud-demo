package com.minguccicommerce.order_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.order_service.client.UserClient;
import com.minguccicommerce.order_service.dto.OrderRequest;
import com.minguccicommerce.order_service.dto.UserDto;
import com.minguccicommerce.order_service.entity.Order;
import com.minguccicommerce.order_service.entity.Product;
import com.minguccicommerce.order_service.repository.OrderRepository;
import com.minguccicommerce.order_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> list() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(orders));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createOrder(@RequestBody OrderRequest request) {
        // 사용자 정보 조회 (Feign)
        UserDto user = userClient.getUserById(request.getUserId());

        // 상품 조회
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        // 재고 확인
        if (product.getStock() < request.getQuantity()) {
            throw new IllegalStateException("상품 재고가 부족합니다.");
        }

        // 재고 차감
        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        // 주문 저장
        Order order = new Order();
        order.setUserId(user.getId());
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        orderRepository.save(order);

        // 응답 메시지
        String message = String.format("✅ [%s] 님이 [%s] 상품을 [%d개] 주문했습니다. 남은 재고: %d",
                user.getName(), product.getName(), request.getQuantity(), product.getStock());

        return ResponseEntity.ok(ApiResponse.ok(message));
    }
}