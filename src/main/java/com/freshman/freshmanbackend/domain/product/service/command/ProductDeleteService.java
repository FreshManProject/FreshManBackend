package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductSale;
import com.freshman.freshmanbackend.domain.product.service.query.ProductOneService;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

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
    Product product = productOneService.getOne(productSeq, Boolean.TRUE);

    // 상품 삭제 처리
    product.delete();
  }

  /**
   * 상품 할인정보 삭제
   *
   * @param productSeq 상품 일련번호
   */
  @Transactional
  public void deleteSale(Long productSeq) {
    // 상품 조회
    Product product = productOneService.getOne(productSeq, Boolean.TRUE);

    // 할인정보 존재여부 검증
    verifySaleNotExists(product.getSale());

    // 상품 할인정보 삭제 처리
    product.deleteSale();
  }

  /**
   * 할인정보 존재여부 검증
   */
  private void verifySaleNotExists(ProductSale sale) {
    if (sale == null) {
      throw new ValidationException("product.sale.not_exists");
    }
  }
}
