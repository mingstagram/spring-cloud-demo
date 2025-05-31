package com.minguccicommerce.payment_service.repository;

import com.minguccicommerce.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPgTransactionId(String pgTransactionId);
}