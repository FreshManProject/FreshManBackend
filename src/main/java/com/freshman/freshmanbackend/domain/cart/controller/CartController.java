package com.freshman.freshmanbackend.domain.cart.controller;

import com.freshman.freshmanbackend.domain.cart.controller.validator.CartValidator;
import com.freshman.freshmanbackend.domain.cart.request.CartEntryRequest;
import com.freshman.freshmanbackend.domain.cart.request.CartUpdateRequest;
import com.freshman.freshmanbackend.domain.cart.response.CartInfoResponse;
import com.freshman.freshmanbackend.domain.cart.service.CartService;
import com.freshman.freshmanbackend.global.common.response.ListResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 장바구니 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
  public final CartService cartService;

  @PostMapping
  public ResponseEntity<?> add(@RequestBody CartEntryRequest cartEntryRequest) {
    CartValidator.validate(cartEntryRequest);
    cartService.add(cartEntryRequest);
    return ResponseEntity.ok(new SuccessResponse());
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<?> delete(@PathVariable Long cartId) {
    cartService.delete(cartId);
    return ResponseEntity.ok(new SuccessResponse());
  }

  @GetMapping
  public ResponseEntity<?> get() {
    List<CartInfoResponse> cartInfoResponses = cartService.getUserCartsList();
    return ResponseEntity.ok(new ListResponse(cartInfoResponses));
  }

  @PutMapping("/{cartId}")
  public ResponseEntity<?> update(@PathVariable Long cartId, @RequestBody CartUpdateRequest cartUpdateRequest) {
    CartValidator.validate(cartUpdateRequest);
    cartService.update(cartUpdateRequest, cartId);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
