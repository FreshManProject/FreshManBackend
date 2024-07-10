package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 할인정보 등록/수정 공통 요청
 */
@Getter
@Setter
public class ProductSaleRequest {

  /**
   * 상품 일련번호
   */
  private Long productSeq;
  /**
   * 할인 가격
   */
  private Long salePrice;
  /**
   * 할인 시작일시
   */
  private String saleStartAt;
  /**
   * 할인 종료일시
   */
  private String saleEndAt;
}
