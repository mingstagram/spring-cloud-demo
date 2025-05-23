package com.minguccicommerce.auth_service.service;

import com.minguccicommerce.auth_service.client.UserClient;
import com.minguccicommerce.auth_service.dto.LoginRequest;
import com.minguccicommerce.auth_service.dto.TokenResponse;
import com.minguccicommerce.auth_service.dto.UserResponse;
import com.minguccicommerce.common_library.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(LoginRequest request) {
        UserResponse user = userClient.getUserByEmail(request.getEmail()).getData();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        redisTemplate.opsForValue().set(
                "refresh:" + user.getId(),
                refreshToken,
                jwtProvider.getRefreshTokenExpireMillis(),
                TimeUnit.MILLISECONDS
        );

        return new TokenResponse(accessToken, refreshToken);

    }

    public TokenResponse reissueAccessToken(String refreshToken) {
        if (jwtProvider.isExpired(refreshToken)) {
            throw new IllegalArgumentException("만료된 리프레시 토큰입니다.");
        }

        Long userId = jwtProvider.extractUserId(refreshToken);
        String savedToken = redisTemplate.opsForValue().get("refresh:" + userId);

        if (!refreshToken.equals(savedToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 일치하지 않습니다.");
        }

        String newAccessToken = jwtProvider.createAccessToken(userId);

        return new TokenResponse(newAccessToken, refreshToken); // refreshToken은 재사용
    }

    public void logout(Long userId) {
        redisTemplate.delete("refresh:" + userId);
    }

}
