package com.freshman.freshmanbackend.domain.product.domain.enums;

import java.util.EnumSet;

import lombok.Getter;

/**
 * 상품 정렬 타입
 *
 * 
 */
@Getter
public enum ProductSortType {

  /**
   * 최신순
   */
  NEWEST("newest"),
  /**
   * 높은 가격순
   */
  HIGHEST("highest"),
  /**
   * 낮은 가격순
   */
  LOWEST("lowest");

  private final String code;

  ProductSortType(String code) {
    this.code = code;
  }

  public static boolean containCode(String code) {
    return EnumSet.allOf(ProductSortType.class).stream().anyMatch(e -> e.getCode().equals(code));
  }
}
