package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 목록 조회 요청
 */
@Getter
@Setter
public class ProductListRequest {

  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 낮은 가격
   */
  private Integer lowPrice;
  /**
   * 높은 가격
   */
  private Integer highPrice;
  /**
   * 정렬
   */
  private String sort;

  /**
   * 페이지
   */
  private Integer page;
}
