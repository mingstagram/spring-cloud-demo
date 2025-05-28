package com.minguccicommerce.order_service.repository;

import com.minguccicommerce.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query("SELECT o.status, COUNT(o) FROM Order o WHERE o.userId = :userId GROUP BY o.status")
    List<Object[]> countOrdersByStatus(@Param("userId") Long userId);

}