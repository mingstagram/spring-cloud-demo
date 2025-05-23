package com.minguccicommerce.user_service.dto;

import com.minguccicommerce.user_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String email;
    private String name; // 닉네임 또는 이름

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getName() // 필드명이 nickname이면 변경 필요
        );
    }
}