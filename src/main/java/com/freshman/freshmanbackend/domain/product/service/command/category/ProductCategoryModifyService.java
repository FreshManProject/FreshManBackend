package com.freshman.freshmanbackend.domain.product.service.command.category;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductCategoryModifyRequest;
import com.freshman.freshmanbackend.domain.product.service.query.category.ProductCategoryOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 카테고리 수정 서비스
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryModifyService {

    private final ProductCategoryOneService productCategoryOneService;

    /**
     * 카테고리 수정
     *
     * @param param 요청 파라미터
     */
    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public void modify(ProductCategoryModifyRequest param) {
        // 카테고리 조회
        ProductCategory category = productCategoryOneService.getOne(param.getCategorySeq());

        // 카테고리 정보 수정
        category.update(param.getName());
    }
}
