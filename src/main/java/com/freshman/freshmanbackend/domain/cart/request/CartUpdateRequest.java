package com.freshman.freshmanbackend.domain.cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 업데이트 요청 클래스
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateRequest {
  private Integer quantity;
}
