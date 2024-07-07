package com.freshman.freshmanbackend.domain.product.controller.validator;

import com.freshman.freshmanbackend.domain.product.domain.enums.ProductSortType;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 상품 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class ProductValidator {

  /**
   * 상품 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductEntryRequest param) {
    // 상품명
    validateEmpty(param.getName(), "상품명은 필수값입니다.");
    // 상품 가격
    validateNull(param.getPrice(), "상품 가격은 필수값입니다.");
    // 상품 설명
    validateEmpty(param.getDescription(), "상품 설명은 필수값입니다.");
    // 브랜드명
    validateEmpty(param.getBrand(), "브랜드명은 필수값입니다.");
    // 카테고리 일련번호
    validateNull(param.getCategorySeq(), "카테고리 일련번호는 필수값입니다.");
  }

  /**
   * 상품 카테고리 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductCategoryEntryRequest param) {
    // 카테고리명
    validateEmpty(param.getName(), "카테고리명은 필수값입니다.");
  }

  /**
   * 상품 목록 조회 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductListRequest param) {
    // 카테고리 일련번호
    validateNull(param.getCategorySeq(), "카테고리 일련번호는 필수값입니다.");
    // 낮은 가격 > 높은 가격 체크
    if (Objects.nonNull(param.getLowPrice()) && Objects.nonNull(param.getHighPrice())) {
      if (param.getLowPrice() > param.getHighPrice()) {
        throw new IllegalArgumentException("낮은 가격은 높은 가격보다 같거나 높아야 합니다.");
      }
    }
    // 정렬 타입
    if (StringUtils.isNotBlank(param.getSort()) && !ProductSortType.containCode(param.getSort())) {
      throw new IllegalArgumentException("유효하지 않은 상품 정렬 타입입니다.");
    }
  }

  private void validateEmpty(String str, String exception) {
    if (StringUtils.isBlank(str)) {
      throw new IllegalArgumentException(exception);
    }
  }

  private void validateNull(Object obj, String exception) {
    if (Objects.isNull(obj)) {
      throw new IllegalArgumentException(exception);
    }
  }
}
