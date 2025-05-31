package com.minguccicommerce.cart_service.controller;

import com.minguccicommerce.cart_service.dto.CartItemDto;
import com.minguccicommerce.cart_service.service.CartService;
import com.minguccicommerce.common_library.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addToCart(@AuthenticationPrincipal Long userId, @RequestBody CartItemDto item) {
        cartService.addToCart(userId, item);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // 장바구니 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Map<Object, Object>>> getCart (@AuthenticationPrincipal Long userId, @RequestBody CartItemDto item) {
        return ResponseEntity.ok(ApiResponse.ok(cartService.getCart(userId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(@AuthenticationPrincipal Long userId,
                                                        @PathVariable Long productId) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
