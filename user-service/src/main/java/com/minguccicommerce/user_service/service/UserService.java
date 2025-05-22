package com.minguccicommerce.user_service.service;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.user_service.client.EmailVerifyClient;
import com.minguccicommerce.user_service.dto.EmailVerifyRequest;
import com.minguccicommerce.user_service.dto.SignUpRequest;
import com.minguccicommerce.user_service.entity.User;
import com.minguccicommerce.user_service.exception.UserNotFoundException;
import com.minguccicommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailVerifyClient emailVerifyClient;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
    }

    public void signup(SignUpRequest request) {
        ApiResponse<String> response = emailVerifyClient.verifyCode(new EmailVerifyRequest(request.getEmail(), request.getEmailCode()));
        if(!response.isSuccess()) {
            throw new IllegalArgumentException("이메일 인증 실패: " + response.getMessage());
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 암호화
        user.setVerified(true); // 이메일 인증 완료 상태로 저장

        userRepository.save(user);

    }

}
