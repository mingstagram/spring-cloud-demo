package com.minguccicommerce.order_service.entity;

import com.minguccicommerce.order_service.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Order(Long userId, Long productId, Integer quantity, OrderStatus status) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}