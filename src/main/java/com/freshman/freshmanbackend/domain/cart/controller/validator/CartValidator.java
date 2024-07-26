package com.freshman.freshmanbackend.domain.cart.controller.validator;

import com.freshman.freshmanbackend.domain.cart.request.CartEntryRequest;
import com.freshman.freshmanbackend.domain.cart.request.CartUpdateRequest;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 장바구니 검증
 */
@UtilityClass
public class CartValidator {
  /**
   * 장바구니 추가 요청 검증
   *
   * @param cartEntryRequest
   * @throws ValidationException
   */
  public void validate(CartEntryRequest cartEntryRequest) throws ValidationException {
    validateNull(cartEntryRequest.getProductSeq(), "cart.entry_request.product_seq_null");
    validateNull(cartEntryRequest.getQuantity(), "cart.entry_request.quantity_null");
  }

  public void validate(CartUpdateRequest cartUpdateRequest) throws ValidationException {
    validateNull(cartUpdateRequest.getQuantity(), "cart.update_request.quantity_null");
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
