package com.freshman.freshmanbackend.domain.product.service;

import com.freshman.freshmanbackend.domain.product.dao.ProductListDao;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.response.ProductListResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 상품 목록 조회 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ProductListService {

  private final ProductListDao productListDao;

  /**
   * 상품 목록 조회
   *
   * @param param 요청 파라미터
   * @return 상품 목록
   */
  @Transactional(readOnly = true)
  public List<ProductListResponse> getList(ProductListRequest param) {
    return productListDao.select(param);
  }
}
