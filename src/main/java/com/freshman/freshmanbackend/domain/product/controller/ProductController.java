package com.freshman.freshmanbackend.domain.product.controller;

import com.freshman.freshmanbackend.domain.product.controller.validator.ProductValidator;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSaleRequest;
import com.freshman.freshmanbackend.domain.product.service.command.ProductCategoryDeleteService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductCategoryEntryService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductCategoryModifyService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductDeleteService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductEntryService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductModifyService;
import com.freshman.freshmanbackend.domain.product.service.query.ProductCategoryListService;
import com.freshman.freshmanbackend.domain.product.service.query.ProductListService;
import com.freshman.freshmanbackend.domain.product.service.query.ProductOneService;
import com.freshman.freshmanbackend.global.common.response.DataResponse;
import com.freshman.freshmanbackend.global.common.response.ListResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  private final ProductModifyService productModifyService;
  private final ProductDeleteService productDeleteService;
  private final ProductListService productListService;
  private final ProductOneService productOneService;

  private final ProductCategoryEntryService productCategoryEntryService;
  private final ProductCategoryModifyService productCategoryModifyService;
  private final ProductCategoryDeleteService productCategoryDeleteService;
  private final ProductCategoryListService productCategoryListService;

  /**
   * 상품 삭제
   *
   * @param productSeq 상품 일련번호
   * @return 요청 결과
   */
  @DeleteMapping
  public ResponseEntity<?> doDelete(@RequestParam(required = false) Long productSeq) {
    ProductValidator.validateProductSeq(productSeq);

    productDeleteService.delete(productSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 카테고리 삭제
   *
   * @param categorySeq 카테고리 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/categories")
  public ResponseEntity<?> doDeleteCategory(@RequestParam(required = false) Long categorySeq) {
    ProductValidator.validateCategorySeq(categorySeq);

    productCategoryDeleteService.delete(categorySeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 할인정보 삭제
   *
   * @param productSeq 상품 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/sales")
  public ResponseEntity<?> doDeleteSale(@RequestParam(required = false) Long productSeq) {
    ProductValidator.validateProductSeq(productSeq);

    productDeleteService.deleteSale(productSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

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
   * 상품 상세 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/{productSeq}")
  public ResponseEntity<?> doGetDetail(@PathVariable Long productSeq) {
    return ResponseEntity.ok(new DataResponse(productOneService.getOne(productSeq)));
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

  /**
   * 상품 할인정보 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping("/sales")
  public ResponseEntity<?> doPostSale(@RequestBody ProductSaleRequest param) {
    ProductValidator.validate(param);

    productEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody ProductModifyRequest param) {
    ProductValidator.validate(param);

    productModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 카테고리 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping("/categories")
  public ResponseEntity<?> doPutCategory(@RequestBody ProductCategoryModifyRequest param) {
    ProductValidator.validate(param);

    productCategoryModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 할인정보 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping("/sales")
  public ResponseEntity<?> doPutSale(@RequestBody ProductSaleRequest param) {
    ProductValidator.validate(param);

    productModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
