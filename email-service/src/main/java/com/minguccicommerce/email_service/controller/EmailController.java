package com.minguccicommerce.email_service.controller;

import com.minguccicommerce.email_service.dto.EmailSendRequest;
import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.email_service.dto.EmailVerifyRequest;
import com.minguccicommerce.email_service.exception.EmailCodeMismatchException;
import com.minguccicommerce.email_service.service.EmailSendService;
import com.minguccicommerce.email_service.service.EmailVerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailSendService emailSendService;
    private final EmailVerifyService emailVerifyService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendEmailCode(@Valid @RequestBody EmailSendRequest request) {
        emailSendService.sendCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.ok("이메일 인증코드 전송 완료"));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyEmailCode(@RequestBody @Valid EmailVerifyRequest request) {
        boolean isValid =  emailVerifyService.verifyCode(request.getEmail(), request.getCode());
        if (isValid) {
            return ResponseEntity.ok(ApiResponse.ok("인증 성공"));
        } else {
            return ResponseEntity.ok(ApiResponse.fail("인증 실패: 잘못된 코드입니다"));
        }
    }
}
