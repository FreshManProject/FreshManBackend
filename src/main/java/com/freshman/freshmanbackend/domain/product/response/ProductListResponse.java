package com.freshman.freshmanbackend.domain.product.response;

import lombok.Getter;

/**
 * 상품 목록 조회 응답
 */
@Getter
public class ProductListResponse {

  /**
   * 상품 일련번호
   */
  private final Long productSeq;
  /**
   * 상품명
   */
  private final String name;
  /**
   * 가격
   */
  private final Long price;
  /**
   * 브랜드명
   */
  private final String brand;
  /**
   * 목록 이미지 url
   */
  private final String imageUrl;

  public ProductListResponse(Long productSeq, String name, Long price, String brand) {
    this.productSeq = productSeq;
    this.name = name;
    this.price = price;
    this.brand = brand;
    this.imageUrl = "";
  }
}
