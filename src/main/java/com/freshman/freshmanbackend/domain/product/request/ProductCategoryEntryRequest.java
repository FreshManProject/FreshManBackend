package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 카테고리 등록 요청
 */
@Getter
@Setter
public class ProductCategoryEntryRequest {

  /**
   * 카테고리명
   */
  private String name;
}
