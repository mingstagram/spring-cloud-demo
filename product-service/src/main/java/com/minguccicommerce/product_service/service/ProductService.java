package com.minguccicommerce.product_service.service;

import com.minguccicommerce.product_service.document.ProductDocument;
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

    public void decreaseStockWithRetry(Long id, int quantity) {
        int maxTry = 3;
        long backoff = 30L; // ms

        for (int i = 0; i < maxTry; i++) {
            try {
                decreaseStockOnce(id, quantity); // 트랜잭션 1회 시도
                return; // 성공
            } catch (org.springframework.dao.OptimisticLockingFailureException e) {
                sleep(backoff);
                backoff *= 2; // 지수 백오프
            }
        }
        throw new org.springframework.dao.OptimisticLockingFailureException("재고 차감 충돌 다수 발생");
    }

    @Transactional
    public void decreaseStockOnce(Long id, int quantity) {
        Product product = findById(id);   // version 포함 로드
        product.decreaseStock(quantity);  // 도메인 검증 + 차감
        // flush/save 불필요: 트랜잭션 커밋 시점에 업데이트 + 버전 증가
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
        productSearchRepository.save(ProductDocument.from(product));

        return saved;
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findById(id);

        product.update(request.getName(), request.getDescription(), request.getPrice(), request.getStock(), request.getCategory());

        // DB 저장 후 Elasticsearch 업데이트
        productRepository.save(product);
        productSearchRepository.save(ProductDocument.from(product)); // Elasticsearch 색인 갱신

        return ProductResponse.from(product);
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

}
