package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.repository.ProductCategoryRepository;
import com.freshman.freshmanbackend.domain.product.response.ProductCategoryListResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 상품 카테고리 목록 조회 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryListService {

  private final ProductCategoryRepository productCategoryRepository;

  /**
   * 상품 카테고리 목록 조회
   *
   * @return 상품 카테고리 목록
   */
  @Transactional(readOnly = true)
  public List<ProductCategoryListResponse> getList() {
    return productCategoryRepository.findAll()
                                    .stream()
                                    .sorted(Comparator.comparing(ProductCategory::getOrder))
                                    .map(ProductCategoryListResponse::new)
                                    .toList();
  }
}
