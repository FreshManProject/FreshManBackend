package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 후기 등록 요청
 */
@Getter
@Setter
public class ReviewEntryRequest {

  /**
   * 후기 내용
   */
  private String content;
  /**
   * 별점
   */
  private Byte score;
  /**
   * 상품 일련번호
   */
  private Long productSeq;
}
