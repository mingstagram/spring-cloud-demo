package com.minguccicommerce.user_service.controller;

import com.minguccicommerce.common_library.ApiResponse;
import com.minguccicommerce.common_library.exception.UserNotFoundException;
import com.minguccicommerce.user_service.entity.User;
import com.minguccicommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.ok(saved));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(userRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
        return ResponseEntity.ok(ApiResponse.ok(user));
    }
}