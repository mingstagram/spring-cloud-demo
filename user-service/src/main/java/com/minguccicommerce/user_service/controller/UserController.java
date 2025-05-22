package com.minguccicommerce.user_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.user_service.dto.SignUpRequest;
import com.minguccicommerce.user_service.entity.User;
import com.minguccicommerce.user_service.exception.UserNotFoundException;
import com.minguccicommerce.user_service.repository.UserRepository;
import com.minguccicommerce.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}