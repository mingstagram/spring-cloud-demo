package com.minguccicommerce.product_service.service;

import com.minguccicommerce.product_service.document.ProductDocument;
import com.minguccicommerce.product_service.dto.ProductResponse;
import com.minguccicommerce.product_service.dto.ProductSearchRequest;
import com.minguccicommerce.product_service.dto.ProductSearchResponse;
import com.minguccicommerce.product_service.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public ProductSearchResponse search(ProductSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<ProductDocument> page = productSearchRepository.findByNameContainingOrDescriptionContainingOrCategoryContaining(
                request.getKeyword(), request.getKeyword(), request.getKeyword(), pageable
        );
        List<ProductResponse> results = page.getContent().stream()
                .map(doc -> new ProductResponse(
                        doc.getId(), doc.getName(), doc.getDescription(),
                        doc.getPrice(), doc.getCategory()))
                .toList();

        return new ProductSearchResponse(results, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

}
