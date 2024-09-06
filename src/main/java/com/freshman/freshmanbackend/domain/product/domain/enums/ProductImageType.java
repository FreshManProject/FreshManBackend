package com.freshman.freshmanbackend.domain.product.domain.enums;

import com.freshman.freshmanbackend.global.common.domain.converter.AbstractEnumCodeConverter;
import com.freshman.freshmanbackend.global.common.domain.enums.Codable;

import java.util.EnumSet;

import jakarta.persistence.Converter;
import lombok.Getter;

/**
 * 상품 이미지 타입
 *
 * 
 */
@Getter
public enum ProductImageType implements Codable {

  /**
   * 목록 이미지
   */
  LIST("L"),
  /**
   * 상세 이미지
   */
  DETAIL("D");

  private final String code;

  ProductImageType(String code) {
    this.code = code;
  }

  public static boolean containCode(String code) {
    return EnumSet.allOf(ProductImageType.class).stream().anyMatch(e -> e.getCode().equals(code));
  }

  public static ProductImageType fromCode(final String code) {
    return Codable.fromCode(ProductImageType.class, code);
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<ProductImageType> {
    @Override
    public ProductImageType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(ProductImageType.class, dbData);
    }
  }
}
