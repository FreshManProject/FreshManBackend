package com.freshman.freshmanbackend.domain.product.service;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 상품 등록 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ProductEntryService {

  private final ProductRepository productRepository;

  private final ProductCategoryOneService productCategoryOneService;

  /**
   * 상품 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(ProductEntryRequest param) {
    // 카테고리 조회
    ProductCategory category = productCategoryOneService.getOne(param.getCategorySeq());

    // 상품 등록
    productRepository.save(
        new Product(param.getName(), param.getPrice(), param.getDescription(), param.getBrand(), category));
  }
}
