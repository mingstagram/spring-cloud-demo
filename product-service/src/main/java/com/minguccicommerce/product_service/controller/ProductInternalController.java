package com.minguccicommerce.product_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.product_service.exception.InsufficientStockException;
import com.minguccicommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class ProductInternalController {

    private final ProductService productService;

    /**
     * 재고 차감 (내부 호출용)
     * - 낙관적 락 + 재시도
     * - 품절/경합 구분 응답
     */
    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<ApiResponse<Void>> decreaseStock(@PathVariable Long id,
                                                           @RequestParam int quantity) {
        try {
            productService.decreaseStockWithRetry(id, quantity);
            return ResponseEntity.ok(ApiResponse.success());

        } catch (InsufficientStockException e) {
            return ResponseEntity.status(409).body(ApiResponse.fail("sold_out"));

        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(409).body(ApiResponse.fail("conflict_retry"));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.fail("internal_error"));
        }
    }

    /**
     * 재고 증가 (단순 트랜잭션)
     */
    @PostMapping("/{id}/increase-stock")
    public ResponseEntity<ApiResponse<Void>> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
        productService.increaseStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
