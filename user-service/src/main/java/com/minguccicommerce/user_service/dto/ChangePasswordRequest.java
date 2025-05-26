package com.minguccicommerce.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다.")
    private String newPassword;

}