package com.freshman.freshmanbackend.domain.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

/**
 * 상품 목록 조회 응답
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
   * 할인 정보
   */
  private Sale sale;
  /**
   * 브랜드명
   */
  private final String brand;
  /**
   * 목록 이미지
   */
  private final String image;

  public ProductListResponse(Long productSeq, String name, Long price, Long salePrice, String brand) {
    this.productSeq = productSeq;
    this.name = name;
    this.price = price;
    if (salePrice != null) {
      this.sale = new Sale(salePrice, (int) (((float) (price - salePrice) / price) * 100));
    }
    this.brand = brand;
    this.image = "";
  }

  @Getter
  public static class Sale {
    /**
     * 할인 가격
     */
    private final Long salePrice;
    /**
     * 할인율
     */
    private final Integer saleRate;

    public Sale(Long salePrice, Integer saleRate) {
      this.salePrice = salePrice;
      this.saleRate = saleRate;
    }
  }
}
