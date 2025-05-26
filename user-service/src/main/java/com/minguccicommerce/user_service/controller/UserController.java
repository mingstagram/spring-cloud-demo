package com.minguccicommerce.user_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.user_service.dto.*;
import com.minguccicommerce.user_service.entity.User;
import com.minguccicommerce.user_service.repository.UserRepository;
import com.minguccicommerce.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignUpRequest request) {
        userService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok("회원가입 완료"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.findById(id)));
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(@RequestParam("email") String email) {
        System.out.println("📌 이메일로 사용자 조회 시작: " + email);
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(ApiResponse.ok(UserResponse.from(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyInfo(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        UserProfileResponse user = userService.getUserInfo(userId);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateMyInfo(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        UserProfileResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@AuthenticationPrincipal Long userId, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUsers(@AuthenticationPrincipal Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}