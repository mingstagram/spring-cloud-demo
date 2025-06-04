package com.minguccicommerce.product_service.service;

import com.minguccicommerce.product_service.document.ProductDocument;
import com.minguccicommerce.product_service.dto.ProductResponse;
import com.minguccicommerce.product_service.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public List<ProductResponse> search(String keyword) {
        List<ProductDocument> docs = productSearchRepository
                .findByNameContainingOrDescriptionContainingOrCategoryContaining(keyword, keyword, keyword);

        return docs.stream()
                .map(doc -> new ProductResponse(
                        doc.getId(),
                        doc.getName(),
                        doc.getDescription(),
                        doc.getPrice(),
                        doc.getCategory()
                )).collect(Collectors.toList());
    }

}
