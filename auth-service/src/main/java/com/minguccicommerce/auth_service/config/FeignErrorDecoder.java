package com.minguccicommerce.auth_service.config;

import com.minguccicommerce.auth_service.exception.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new UserNotFoundException("사용자를 찾을 수 없습니다 (Feign 404)");
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
