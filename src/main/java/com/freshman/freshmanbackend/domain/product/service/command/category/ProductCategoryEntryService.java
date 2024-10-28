package com.freshman.freshmanbackend.domain.product.service.command.category;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductCategoryEntryRequest;
import com.freshman.freshmanbackend.domain.product.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 카테고리 등록 서비스
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryEntryService {

    private final ProductCategoryRepository productCategoryRepository;

    /**
     * 상품 카테고리 등록
     *
     * @param param 요청 파라미터
     */
    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public void entry(ProductCategoryEntryRequest param) {
        // 순서 조회
        int order =
                productCategoryRepository.findTopByOrderByOrderDesc().map(category -> category.getOrder() + 1)
                        .orElse(1);

        // 카테고리 등록
        ProductCategory category = productCategoryRepository.save(new ProductCategory(param.getName(), order));
    }
}
