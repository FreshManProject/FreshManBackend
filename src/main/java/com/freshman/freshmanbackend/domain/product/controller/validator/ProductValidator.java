package com.freshman.freshmanbackend.domain.product.controller.validator;

import com.freshman.freshmanbackend.domain.product.domain.enums.ProductSortType;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSaleRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewEntryRequest;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import com.freshman.freshmanbackend.global.common.utils.DateTimeUtils;

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
    // 상품 기본 정보
    validate(param.getName(), param.getPrice(), param.getDescription(), param.getBrand(), param.getCategorySeq());
  }

  /**
   * 상품 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductModifyRequest param) {
    // 상품 일련번호
    validateProductSeq(param.getProductSeq());
    // 상품 기본 정보
    validate(param.getName(), param.getPrice(), param.getDescription(), param.getBrand(), param.getCategorySeq());
  }

  /**
   * 상품 할인정보 등록/수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductSaleRequest param) {
    // 상품 일련번호
    validateProductSeq(param.getProductSeq());
    // 할인 가격
    validateNull(param.getSalePrice(), "product.sale.param_sale_price_empty");
    // 할인 시작일시
    validateEmpty(param.getSaleStartAt(), "product.sale.param_sale_start_at_empty");
    if (!DateTimeUtils.validFormat(param.getSaleStartAt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("datetime.param_format");
    }
    // 할인 종료일시
    validateEmpty(param.getSaleEndAt(), "product.sale.param_sale_end_at_empty");
    if (!DateTimeUtils.validFormat(param.getSaleEndAt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("datetime.param_format");
    }
    if (!DateTimeUtils.isBeforeDateTime(param.getSaleStartAt(), param.getSaleEndAt())) {
      throw new ValidationException("datetime.param_end_before_start");
    }
  }

  /**
   * 상품 카테고리 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductCategoryEntryRequest param) {
    // 카테고리명
    validateEmpty(param.getName(), "product.category.param_name_empty");
  }

  /**
   * 후기 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ReviewEntryRequest param) {
    // 후기 내용
    validateEmpty(param.getContent(), "review.param_content_empty");
    // 별점
    Byte score = param.getScore();
    validateNull(score, "review.param_score_null");
    if (score < 1 || score > 5) {
      throw new ValidationException("review.param_score_invalid");
    }
    // 상품 일련번호
    validateProductSeq(param.getProductSeq());
  }

  /**
   * 상품 카테고리 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductCategoryModifyRequest param) {
    // 카테고리 일련번호
    validateCategorySeq(param.getCategorySeq());
    // 카테고리명
    validateEmpty(param.getName(), "product.category.param_name_empty");
  }

  /**
   * 상품 목록 조회 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(ProductListRequest param) {
    // 카테고리 일련번호
    validateNull(param.getCategorySeq(), "product.param_category_null");
    // 낮은 가격 > 높은 가격 체크
    if (Objects.nonNull(param.getLowPrice()) && Objects.nonNull(param.getHighPrice())) {
      if (param.getLowPrice() > param.getHighPrice()) {
        throw new ValidationException("product.param_price_search_rule");
      }
    }
    // 정렬 타입
    if (StringUtils.isNotBlank(param.getSort()) && !ProductSortType.containCode(param.getSort())) {
      throw new ValidationException("product.param_sort_invalid");
    }
  }

  public void validateCategorySeq(Long categorySeq) {
    // 카테고리 일련번호
    validateNull(categorySeq, "product.category.param_seq_null");
  }

  public void validateProductSeq(Long productSeq) {
    // 상품 일련번호
    validateNull(productSeq, "product.param_seq_null");
  }

  /**
   * 상품 등록/수정 요청 공통 유효성 체크
   */
  private void validate(String name, Long price, String description, String brand, Long categorySeq) {
    // 상품명
    validateEmpty(name, "product.param_name_empty");
    // 상품 가격
    validateNull(price, "product.param_price_null");
    // 상품 설명
    validateEmpty(description, "product.param_description_empty");
    // 브랜드명
    validateEmpty(brand, "product.param_brand_empty");
    // 카테고리 일련번호
    validateNull(categorySeq, "product.param_category_null");
  }

  private void validateEmpty(String str, String exception) {
    if (StringUtils.isBlank(str)) {
      throw new ValidationException(exception);
    }
  }

  private void validateNull(Object obj, String exception) {
    if (Objects.isNull(obj)) {
      throw new ValidationException(exception);
    }
  }
}
