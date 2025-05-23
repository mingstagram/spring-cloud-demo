package com.minguccicommerce.auth_service.controller;

import com.minguccicommerce.auth_service.dto.LoginRequest;
import com.minguccicommerce.auth_service.dto.TokenResponse;
import com.minguccicommerce.auth_service.service.AuthService;
import com.minguccicommerce.common_library.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(tokens));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshAccessToken(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String refreshToken = authorizationHeader.replace("Bearer ", "").trim();
        TokenResponse token = authService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.ok(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        authService.logout(userId);
        return ResponseEntity.ok(ApiResponse.ok("로그아웃되었습니다."));
    }

}
