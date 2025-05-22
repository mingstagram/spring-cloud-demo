package com.minguccicommerce.email_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final StringRedisTemplate redisTemplate;

    public boolean verifyCode(String email, String code) {
        String key = "email:code:" + email;
        String savedCode = redisTemplate.opsForValue().get(key);

        log.info("✅ Redis에서 가져온 인증코드: {}", savedCode);
        log.info("✅ 사용자 입력 인증코드: {}", code);

        return Objects.equals(savedCode, code);
    }
}
