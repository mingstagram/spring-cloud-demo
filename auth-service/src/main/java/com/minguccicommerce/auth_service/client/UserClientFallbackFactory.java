package com.minguccicommerce.auth_service.client;

import com.minguccicommerce.auth_service.dto.UserDto;
import com.minguccicommerce.auth_service.dto.UserResponse;
import com.minguccicommerce.auth_service.exception.UserNotFoundException;
import com.minguccicommerce.common_library.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public ApiResponse<UserResponse> getUserByEmail(String email) {
                log.warn("⚠️ Feign fallback triggered for getUserById. Reason: {}", cause.getMessage());
                throw new UserNotFoundException("user-service 응답 실패. 사용자 정보를 가져올 수 없습니다.");
            }
        };
    }
}