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
 * 상품 수정 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ProductModifyService {

  private final ProductOneService productOneService;
  private final ProductCategoryOneService productCategoryOneService;

  /**
   * 상품 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(ProductModifyRequest param) {
    // 상품 및 카테고리 조회
    Product product = productOneService.getOne(param.getProductSeq(), Valid.TRUE);
    ProductCategory category = productCategoryOneService.getOne(param.getCategorySeq());

    // 상품 정보 수정
    product.update(param.getName(), param.getPrice(), param.getDescription(), param.getBrand(), category);
  }
}
