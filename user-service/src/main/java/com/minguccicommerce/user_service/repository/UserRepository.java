package com.minguccicommerce.user_service.repository;

import com.minguccicommerce.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}