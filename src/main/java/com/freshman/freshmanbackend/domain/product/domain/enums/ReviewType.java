package com.freshman.freshmanbackend.domain.product.domain.enums;

import com.freshman.freshmanbackend.global.common.domain.converter.AbstractEnumCodeConverter;
import com.freshman.freshmanbackend.global.common.domain.enums.Codable;

import java.util.EnumSet;

import jakarta.persistence.Converter;
import lombok.Getter;

/**
 * 후기 타입
 *
 * @author 송병선
 */
@Getter
public enum ReviewType implements Codable {

  /**
   * 일반 후기
   */
  NORMAL("N"),
  /**
   * 상품 사진 후기
   */
  IMAGE("I"),
  /**
   * 스타일 후기
   */
  STYLE("S");

  private final String code;

  ReviewType(String code) {
    this.code = code;
  }

  public static boolean containCode(String code) {
    return EnumSet.allOf(ReviewType.class).stream().anyMatch(e -> e.getCode().equals(code));
  }

  public static ReviewType fromCode(final String code) {
    return Codable.fromCode(ReviewType.class, code);
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<ReviewType> {
    @Override
    public ReviewType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(ReviewType.class, dbData);
    }
  }
}
