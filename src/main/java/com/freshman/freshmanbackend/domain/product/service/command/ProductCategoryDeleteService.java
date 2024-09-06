package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.repository.ProductCategoryRepository;
import com.freshman.freshmanbackend.domain.product.service.query.ProductCategoryOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 상품 카테고리 삭제 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryDeleteService {

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductCategoryOneService productCategoryOneService;

  /**
   * 카테고리 삭제
   *
   * @param categorySeq 카테고리 일련번호
   */
  @Transactional
  public void delete(Long categorySeq) {
    // 카테고리 조회
    ProductCategory category = productCategoryOneService.getOne(categorySeq);

    // 카테고리 삭제 처리
    productCategoryRepository.delete(category);
  }
}
