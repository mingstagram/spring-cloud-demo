package com.minguccicommerce.user_service.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String emailCode; // 이메일 인증 코드
}