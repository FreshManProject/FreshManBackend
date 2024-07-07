package com.freshman.freshmanbackend.domain.product.response;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;

import lombok.Getter;

/**
 * 상품 카테고리 목록 조회 응답
 */
@Getter
public class ProductCategoryListResponse {

  /**
   * 상품 카테고리 일련번호
   */
  private final Long categorySeq;
  /**
   * 카테고리명
   */
  private final String name;

  public ProductCategoryListResponse(ProductCategory category) {
    this.categorySeq = category.getCategorySeq();
    this.name = category.getName();
  }
}
