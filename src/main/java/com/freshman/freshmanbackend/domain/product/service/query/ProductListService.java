package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.dao.ProductListDao;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSearchRequest;
import com.freshman.freshmanbackend.domain.product.response.ProductListResponse;
import com.freshman.freshmanbackend.domain.product.service.SearchLogService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 상품 목록 조회 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductListService {

  private final ProductListDao productListDao;

  private final SearchLogService searchLogService;

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

  /**
   * 상품 검색 조회
   *
   * @param param 요청 파라미터
   * @return 상품 목록
   */
  @Transactional(readOnly = true)
  public List<ProductListResponse> getList(ProductSearchRequest param) {
    // 최근 검색어 등록
    searchLogService.entry(param.getKeyword());
    return productListDao.select(param);
  }
}
