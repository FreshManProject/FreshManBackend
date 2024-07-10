package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.request.ProductModifyRequest;
import com.freshman.freshmanbackend.domain.product.service.query.ProductCategoryOneService;
import com.freshman.freshmanbackend.domain.product.service.query.ProductOneService;
import com.freshman.freshmanbackend.global.common.domain.enums.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 상품 삭제 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ProductDeleteService {

  private final ProductOneService productOneService;

  /**
   * 상품 삭제
   *
   * @param productSeq 상품 일련번호
   */
  @Transactional
  public void delete(Long productSeq) {
    // 상품 조회
    Product product = productOneService.getOne(productSeq, Valid.TRUE);

    // 상품 삭제 처리
    product.delete();
  }
}
