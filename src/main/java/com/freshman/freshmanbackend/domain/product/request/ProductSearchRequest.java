package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 검색 조회 요청
 */
@Getter
@Setter
public class ProductSearchRequest {

  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 검색 키워드
   */
  private String keyword;
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
  private Integer page = 0;
}
