package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.repository.ProductCategoryRepository;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 상품 카테고리 단일 조회 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryOneService {

  private final ProductCategoryRepository productCategoryRepository;

  /**
   * 상품 카테고리 엔티티 단일 조회
   *
   * @param categorySeq 카테고리 일련번호
   * @return 상품 카테고리 엔티티
   */
  @Transactional(readOnly = true)
  public ProductCategory getOne(Long categorySeq) {
    return productCategoryRepository.findById(categorySeq)
                                    .orElseThrow(() -> new ValidationException("product.category.not_found"));
  }
}
