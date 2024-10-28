package com.freshman.freshmanbackend.domain.product.dto.response;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 카테고리 목록 조회 응답
 */
@Getter
@NoArgsConstructor
public class ProductCategoryResponse {

    /**
     * 상품 카테고리 일련번호
     */
    private Long categorySeq;
    /**
     * 카테고리명
     */
    private String name;

    public ProductCategoryResponse(ProductCategory category) {
        this.categorySeq = category.getCategorySeq();
        this.name = category.getName();
    }
}
