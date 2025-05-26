package com.minguccicommerce.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    private boolean isVerified = false; // 이메일 인증 여부

    public void changePassword(String newEncodedPassword) {
        this.password = newEncodedPassword;
    }
}