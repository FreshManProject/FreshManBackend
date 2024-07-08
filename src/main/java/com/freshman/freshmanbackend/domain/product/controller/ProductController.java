package com.freshman.freshmanbackend.domain.product.controller;

import com.freshman.freshmanbackend.domain.product.controller.validator.ProductValidator;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.service.ProductCategoryEntryService;
import com.freshman.freshmanbackend.domain.product.service.ProductCategoryListService;
import com.freshman.freshmanbackend.domain.product.service.ProductEntryService;
import com.freshman.freshmanbackend.domain.product.service.ProductListService;
import com.freshman.freshmanbackend.global.common.response.ListResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 상품 API
 *
 * @author 송병선
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductEntryService productEntryService;
  private final ProductListService productListService;

  private final ProductCategoryEntryService productCategoryEntryService;
  private final ProductCategoryListService productCategoryListService;

  /**
   * 상품 카테고리 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/categories")
  public ResponseEntity<?> doGetCategoryList() {
    return ResponseEntity.ok(new ListResponse(productCategoryListService.getList()));
  }

  /**
   * 상품 목록 조회
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @GetMapping
  public ResponseEntity<?> doGetList(@ModelAttribute ProductListRequest param) {
    ProductValidator.validate(param);

    return ResponseEntity.ok(new ListResponse(productListService.getList(param)));
  }

  /**
   * 상품 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestBody ProductEntryRequest param) {
    ProductValidator.validate(param);

    productEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 카테고리 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping("/categories")
  public ResponseEntity<?> doPostCategory(@RequestBody ProductCategoryEntryRequest param) {
    ProductValidator.validate(param);

    productCategoryEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
