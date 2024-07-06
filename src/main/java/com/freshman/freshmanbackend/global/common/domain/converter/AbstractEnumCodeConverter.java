package com.freshman.freshmanbackend.global.common.domain.converter;

import com.freshman.freshmanbackend.global.common.domain.enums.Codable;

import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.AttributeConverter;

/**
 * JPA Entity Enum Converter
 *
 * @author 송병선
 */
public abstract class AbstractEnumCodeConverter<E extends Enum<E> & Codable> implements AttributeConverter<E, String> {
  
  @Override
  public String convertToDatabaseColumn(E attribute) {
    return this.toDatabaseColumn(attribute);
  }

  @Override
  public E convertToEntityAttribute(String dbData) {
    return null;
  }

  public E toEntityAttribute(Class<E> cls, String code) {
    return StringUtils.isBlank(code) ? null : Codable.fromCode(cls, code);
  }

  private String toDatabaseColumn(E attr) {
    return (attr == null) ? null : attr.getCode();
  }
}
