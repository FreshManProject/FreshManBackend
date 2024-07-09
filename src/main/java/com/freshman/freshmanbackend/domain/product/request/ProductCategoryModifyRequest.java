package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 카테고리 수정 요청
 */
@Getter
@Setter
public class ProductCategoryModifyRequest {

  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 카테고리명
   */
  private String name;
}
