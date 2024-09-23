package com.freshman.freshmanbackend.domain.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductImage;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;

/**
 * 상품 상세 조회 응답
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailResponse {

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
   * 설명
   */
  private final String description;
  /**
   * 브랜드명
   */
  private final String brand;
  /**
   * 상세 이미지 목록
   */
  private final List<String> imageList;

  public ProductDetailResponse(Product product) {
    this.productSeq = product.getProductSeq();
    this.name = product.getName();
    this.price = product.getPrice();
    if (product.getSale() != null) {
      LocalDateTime startAt = product.getSale().getSaleStartAt();
      LocalDateTime endAt = product.getSale().getSaleEndAt();
      LocalDateTime curTime = LocalDateTime.now();

      if ((curTime.isAfter(startAt) || curTime.equals(startAt)) || (curTime.isBefore(endAt) || curTime.equals(endAt))) {
        long salePrice = product.getSale().getSalePrice();
        this.sale = new Sale(salePrice, (int) (((float) (price - salePrice) / price) * 100));
      }
    }
    this.description = product.getDescription();
    this.brand = product.getBrand();
    this.imageList = product.getImageList()
                            .stream()
                            .sorted(Comparator.comparing(ProductImage::getOrder))
                            .map(ProductImage::getPath)
                            .toList();
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
