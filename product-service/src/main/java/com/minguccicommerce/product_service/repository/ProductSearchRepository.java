package com.minguccicommerce.product_service.repository;

import com.minguccicommerce.product_service.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

    // 이름 + 설명 + 카테고리에서 검색 (OR 조건)
    List<ProductDocument> findByNameContainingOrDescriptionContainingOrCategoryContaining(String name, String desc, String category);
}
