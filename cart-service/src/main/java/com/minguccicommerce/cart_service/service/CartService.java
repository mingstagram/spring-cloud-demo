package com.minguccicommerce.cart_service.service;

import com.minguccicommerce.cart_service.client.ProductClient;
import com.minguccicommerce.cart_service.dto.CartItemDto;
import com.minguccicommerce.cart_service.redis.CartRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRedisRepository cartRedisRepository;
    private final ProductClient productClient;

    // 장바구니에 상품 추가
    public void addToCart(Long userId, CartItemDto item) {
        // 상품 존재 유효성 체크
        productClient.getProductById(item.getProductId());
        cartRedisRepository.addItem(userId, item);
    }

    // 장바구니 목록 조회
    public Map<Object, Object> getCart(Long userId) {
        return cartRedisRepository.getCart(userId);
    }

    // 장바구니 특정 상품 제거
    public void removeFromCart(Long userId, Long productId) {
        cartRedisRepository.removeItem(userId, productId);
    }

}
