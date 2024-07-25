package com.freshman.freshmanbackend.domain.cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 장바구니 추가 Request 클래스
 */
@Getter
@AllArgsConstructor
public class CartEntryRequest {
  private Long productSeq;
  private Integer quantity;
}
