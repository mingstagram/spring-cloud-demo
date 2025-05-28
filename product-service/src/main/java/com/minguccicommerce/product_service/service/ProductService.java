package com.minguccicommerce.product_service.service;

import com.minguccicommerce.product_service.dto.ProductResponse;
import com.minguccicommerce.product_service.entity.Product;
import com.minguccicommerce.product_service.exception.ProductNotFoundException;
import com.minguccicommerce.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = findById(id);
        return ProductResponse.from(product);
    }

    @Transactional
    public void decreaseStock(Long id, int quantity) {
        Product product = findById(id);
        product.decreaseStock(quantity);
    }

    @Transactional
    public void increaseStock(Long id, int quantity) {
        Product product = findById(id);
        product.increaseStock(quantity);
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

}
