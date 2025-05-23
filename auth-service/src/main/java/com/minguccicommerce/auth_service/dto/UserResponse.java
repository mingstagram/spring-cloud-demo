package com.minguccicommerce.auth_service.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password; // 암호화된 비밀번호
}