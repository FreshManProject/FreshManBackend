package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.dao.ProductListDao;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSearchRequest;
import com.freshman.freshmanbackend.domain.product.response.ProductListResponse;
import com.freshman.freshmanbackend.domain.product.service.SearchLogService;

import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
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
  private static final int PAGE_SIZE = 10;

  private final ProductListDao productListDao;

  private final SearchLogService searchLogService;

  /**
   * 상품 목록 조회
   *
   * @param param 요청 파라미터
   * @return 상품 목록
   */
  @Transactional(readOnly = true)
  public NoOffsetPageResponse getList(ProductListRequest param) {
    Boolean isEnd = true;
    List<ProductListResponse> products = productListDao.select(param);
    if (products.size() == PAGE_SIZE + 1){
      products.remove(PAGE_SIZE);
      isEnd = false;
    }
    return new NoOffsetPageResponse(products, isEnd);
  }

  /**
   * 상품 검색 조회
   *
   * @param param 요청 파라미터
   * @return 상품 목록
   */
  @Transactional(readOnly = true)
  public NoOffsetPageResponse getList(ProductSearchRequest param) {
    Boolean isEnd = true;
    // 최근 검색어 등록
    searchLogService.entry(param.getKeyword());
    List<ProductListResponse> products = productListDao.select(param);
    if (products.size() == PAGE_SIZE + 1){
      products.remove(PAGE_SIZE);
      isEnd = false;
    }
    return new NoOffsetPageResponse(products, isEnd);
  }
}
