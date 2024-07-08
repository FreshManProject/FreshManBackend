package com.freshman.freshmanbackend.domain.product.service;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 상품 단일 조회 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ProductOneService {

  private final ProductRepository productRepository;

  /**
   * 상품 엔티티 단일 조회
   *
   * @param productSeq 상품 일련번호
   * @return 상품 엔티티
   */
  @Transactional(readOnly = true)
  public Product getOne(Long productSeq) {
    return productRepository.findById(productSeq).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
  }
}
