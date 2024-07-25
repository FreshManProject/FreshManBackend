package com.freshman.freshmanbackend.domain.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductImage;
import com.freshman.freshmanbackend.domain.product.domain.enums.ProductImageType;

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
   * 설명
   */
  private final String description;
  /**
   * 할인 가격
   */
  private Long salePrice;
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
    this.description = product.getDescription();
    if (product.getSale() != null) {
      LocalDateTime startAt = product.getSale().getSaleStartAt();
      LocalDateTime endAt = product.getSale().getSaleEndAt();
      LocalDateTime curTime = LocalDateTime.now();

      if ((curTime.isAfter(startAt) || curTime.equals(startAt)) || (curTime.isBefore(endAt) || curTime.equals(endAt))) {
        salePrice = product.getSale().getSalePrice();
      }
    }
    this.brand = product.getBrand();
    this.imageList = product.getImageList()
                            .stream()
                            .filter(image -> image.getType().equals(ProductImageType.DETAIL))
                            .sorted(Comparator.comparing(ProductImage::getOrder))
                            .map(ProductImage::getPath)
                            .toList();
  }
}
