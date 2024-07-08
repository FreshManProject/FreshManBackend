package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 등록 요청
 */
@Getter
@Setter
public class ProductEntryRequest {

  /**
   * 상품명
   */
  private String name;
  /**
   * 가격
   */
  private Long price;
  /**
   * 설명
   */
  private String description;
  /**
   * 브랜드명
   */
  private String brand;
  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
}
