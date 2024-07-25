package com.freshman.freshmanbackend.domain.cart.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freshman.freshmanbackend.domain.cart.domain.Cart;
import com.freshman.freshmanbackend.domain.product.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 장바구니 리스트 응답 클래스
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartInfoResponse {
  private Long productSeq;
  private String name;
  private Long price;
  private String brand;
  private Integer quantity;

  public static CartInfoResponse fromCart(Cart cart) {
    Product product = cart.getProduct();
    return new CartInfoResponse(product.getProductSeq(), product.getName(), product.getPrice(), product.getBrand(),
        cart.getQuantity());
  }
}
