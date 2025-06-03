package com.minguccicommerce.notification_service.controller;

import com.minguccicommerce.notification_service.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    @MessageMapping("/send")
    @SendTo("/topic/notifications")
    public String sendMessage(String message) {
        return "서버로부터 받은 메시지: " + message;
    }
}
