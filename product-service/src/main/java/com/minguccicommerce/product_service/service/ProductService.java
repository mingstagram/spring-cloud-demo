package com.minguccicommerce.product_service.service;

import com.minguccicommerce.product_service.dto.ProductRequest;
import com.minguccicommerce.product_service.dto.ProductResponse;
import com.minguccicommerce.product_service.entity.Product;
import com.minguccicommerce.product_service.exception.ProductNotFoundException;
import com.minguccicommerce.product_service.repository.ProductRepository;
import com.minguccicommerce.product_service.repository.ProductSearchRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;

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

    @Transactional
    public Product registerProduct(ProductRequest request) {
        Product product = Product.from(request);
        Product saved = productRepository.save(product);

        // Elasticsearch 색인 등록
        productSearchRepository.save(saved.toDocument());

        return saved;
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

}
