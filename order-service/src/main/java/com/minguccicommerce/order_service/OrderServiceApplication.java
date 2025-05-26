package com.minguccicommerce.order_service;

import com.minguccicommerce.common_library.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import(GlobalExceptionHandler.class)
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.minguccicommerce")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
