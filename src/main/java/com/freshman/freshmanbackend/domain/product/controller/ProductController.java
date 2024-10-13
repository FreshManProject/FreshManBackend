package com.freshman.freshmanbackend.domain.product.controller;

import com.freshman.freshmanbackend.domain.product.controller.validator.ProductValidator;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductCategoryModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSaleRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSearchRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewCommentEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewCommentModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewModifyRequest;
import com.freshman.freshmanbackend.domain.product.service.SearchLogService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductCategoryDeleteService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductCategoryEntryService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductCategoryModifyService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductDeleteService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductEntryService;
import com.freshman.freshmanbackend.domain.product.service.command.ProductModifyService;
import com.freshman.freshmanbackend.domain.product.service.command.ReviewDeleteService;
import com.freshman.freshmanbackend.domain.product.service.command.ReviewEntryService;
import com.freshman.freshmanbackend.domain.product.service.command.ReviewModifyService;
import com.freshman.freshmanbackend.domain.product.service.query.*;
import com.freshman.freshmanbackend.global.common.response.DataResponse;
import com.freshman.freshmanbackend.global.common.response.ListResponse;
import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
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

  private final ReviewEntryService reviewEntryService;
  private final ReviewModifyService reviewModifyService;
  private final ReviewDeleteService reviewDeleteService;

  private final SearchLogService searchLogService;
  private final ReviewListService reviewListService;

  /**
   * 상품 삭제
   *
   * @param productSeq 상품 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/{productSeq}")
  public ResponseEntity<?> doDelete(@PathVariable(required = false) Long productSeq) {
    productDeleteService.delete(productSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 카테고리 삭제
   *
   * @param categorySeq 카테고리 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/categories/{categorySeq}")
  public ResponseEntity<?> doDeleteCategory(@PathVariable(required = false) Long categorySeq) {
    productCategoryDeleteService.delete(categorySeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 후기 삭제
   *
   * @param reviewSeq 후기 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/reviews/{reviewSeq}")
  public ResponseEntity<?> doDeleteReview(@PathVariable(required = false) Long reviewSeq) {
    reviewDeleteService.delete(reviewSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 후기 댓글 삭제
   *
   * @param commentSeq 댓글 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/reviews/comments/{commentSeq}")
  public ResponseEntity<?> doDeleteReviewComment(@PathVariable(required = false) Long commentSeq) {
    reviewDeleteService.deleteComment(commentSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 할인정보 삭제
   *
   * @param productSeq 상품 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/sales/{productSeq}")
  public ResponseEntity<?> doDeleteSale(@PathVariable(required = false) Long productSeq) {
    ProductValidator.validateProductSeq(productSeq);

    productDeleteService.deleteSale(productSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 상품 최근 검색어 삭제
   *
   * @return 요청 결과
   */
  @DeleteMapping("/search/keywords")
  public ResponseEntity<?> doDeleteSearchKeyword(@RequestParam(required = false) Long index) {
    searchLogService.delete(index);

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
    return ResponseEntity.ok(productListService.getList(param));
  }

  /**
   * 상품 검색 조회
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @GetMapping("/search")
  public ResponseEntity<?> doGetSearch(@ModelAttribute ProductSearchRequest param) {
    ProductValidator.validate(param);

    return ResponseEntity.ok(productListService.getList(param));
  }

  /**
   * 상품 최근 검색어 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/search/keywords")
  public ResponseEntity<?> doGetSearchKeywordList() {
    return ResponseEntity.ok(new ListResponse(searchLogService.getList()));
  }

  /**
   * 상품 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@ModelAttribute ProductEntryRequest param) {
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
   * 후기 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping("/reviews")
  public ResponseEntity<?> doPostReview(@RequestBody ReviewEntryRequest param) {
    ProductValidator.validate(param);

    reviewEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 후기 댓글 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping("/reviews/comments")
  public ResponseEntity<?> doPostReviewComment(@RequestBody ReviewCommentEntryRequest param) {
    ProductValidator.validate(param);

    reviewEntryService.entry(param);
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
  public ResponseEntity<?> doPut(@ModelAttribute ProductModifyRequest param) {
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
   * 후기 조회
   * @param productSeq 상품 아이디
   * @return 후기 리스트
   */
  @GetMapping("/{productSeq}/reviews")
  public ResponseEntity<?> doGetReviews(@PathVariable Long productSeq, @RequestParam(value = "page", required = false, defaultValue = "0") int page){
    NoOffsetPageResponse reviews = reviewListService.getReviewsByProductSeq(productSeq, page);
    return ResponseEntity.ok(reviews);
  }

  /**
   * 후기 댓글 조회
   * @param reviewSeq 리뷰 아이디
   * @return 후기 댓글 리스트
   */
  @GetMapping("/reviews/{reviewSeq}/comments")
  public ResponseEntity<?> doGetReviewComments(@PathVariable("reviewSeq") Long reviewSeq, @RequestParam("page") int page){
    NoOffsetPageResponse comments = reviewListService.getReviewCommentsByReviewSeq(reviewSeq, page);
    return ResponseEntity.ok(comments);
  }

  /**
   * 후기 대댓글 조회
   * @param commentSeq 댓글 아이디
   * @param page 페이지
   * @return 후기 대댓글 리스트
   */
  @GetMapping("/reviews/comments/{commentSeq}/replies")
  public ResponseEntity<?> doGetReviewCommentReplies(@PathVariable("commentSeq") Long commentSeq, @RequestParam("page") int page){
    NoOffsetPageResponse comments = reviewListService.getReviewCommentsByParentCommentSeq(commentSeq, page);
    return ResponseEntity.ok(comments);
  }

  /**
   * 후기 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping("/reviews")
  public ResponseEntity<?> doPutReview(@RequestBody ReviewModifyRequest param) {
    ProductValidator.validate(param);

    reviewModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 후기 댓글 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping("/reviews/comments")
  public ResponseEntity<?> doPutReviewComment(@RequestBody ReviewCommentModifyRequest param) {
    ProductValidator.validate(param);

    reviewModifyService.modify(param);
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

  @GetMapping("/all")
  public ResponseEntity<?> doGeAllProducts(@RequestParam("page") int page){
    NoOffsetPageResponse allList = productListService.getAllList(page);
    return ResponseEntity.ok(allList);
  }
}
