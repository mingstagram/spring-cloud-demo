package com.minguccicommerce.cart_service.redis;

import com.minguccicommerce.cart_service.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CartRedisRepository {

    private final RedisTemplate<Object, Object> redisTemplate;

    public void addItem(Long userId, CartItemDto item) {
        String key = "cart:" + userId;
        redisTemplate.opsForHash().put(key, item.getProductId(), item);
        redisTemplate.expire(key, Duration.ofDays(7));
    }

    public Map<Object, Object> getCart(Long userId) {
        String key = "cart:" + userId;
        return redisTemplate.opsForHash().entries(key);
    }

    public void removeItem(Long userId, Long productId) {
        String key = "cart:" + userId;
        redisTemplate.opsForHash().delete(key, productId);
    }

}
