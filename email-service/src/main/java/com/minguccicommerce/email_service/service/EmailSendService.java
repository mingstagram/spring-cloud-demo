package com.minguccicommerce.email_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSendService {

    private final RedisTemplate<String, String> redisTemplate;
//    private final JavaMailSender mailSender;

    public void sendCode(String email) {
        String code = generateCode();
        String key = "email:code:" + email;

        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(3));

        log.info("📨 이메일 인증코드 발송 (테스트용 로그)");
        log.info("To: {}", email);
        log.info("인증코드: {}", code);

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("[minguccicommerce] 이메일 인증코드");
//        message.setText("인증코드: " + code);
//        mailSender.send(message);

    }

    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

}
