package com.freshman.freshmanbackend.domain.product.service.query.product;

import com.freshman.freshmanbackend.domain.product.dao.ProductListDao;
import com.freshman.freshmanbackend.domain.product.domain.enums.ProductSortType;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductSearchRequest;
import com.freshman.freshmanbackend.domain.product.dto.request.SaleProductListRequest;
import com.freshman.freshmanbackend.domain.product.dto.response.ProductListResponse;
import com.freshman.freshmanbackend.domain.product.dto.response.ProductRankingResponse;
import com.freshman.freshmanbackend.domain.product.service.SearchLogService;
import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 목록 조회 서비스
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
        boolean isEnd = true;
        Long nextSeq = null;
        Long nextPrice = null;
        Integer nextOrderCount = null;
        List<Long> productsPageSeqList = productListDao.getProductsPageSeqList(param);

        String sort = param.getSort();
        List<ProductListResponse> products = productListDao.getProductInfo(productsPageSeqList, sort);

        if (productsPageSeqList.size() == PAGE_SIZE + 1) {
            nextSeq = productsPageSeqList.get(PAGE_SIZE);

            if (sort != null && (sort.equals(ProductSortType.HIGHEST.getCode()) || sort.equals(
                    ProductSortType.LOWEST.getCode()))) {
                nextPrice = products.get(PAGE_SIZE).getPrice();
            } else if (sort != null && (sort.equals(ProductSortType.HOTTEST.getCode()))) {
                nextOrderCount = products.get(PAGE_SIZE).getOrderCount();
            }

            products.remove(PAGE_SIZE);
            isEnd = false;
        }

        return new NoOffsetPageResponse(products, isEnd, nextSeq, nextPrice, nextOrderCount);
    }

    /**
     * 상품 검색 조회
     *
     * @param param 요청 파라미터
     * @return 상품 목록
     */
    @Transactional(readOnly = true)
    public NoOffsetPageResponse getList(ProductSearchRequest param) {
        boolean isEnd = true;
        // 최근 검색어 등록
        searchLogService.entry(param.getKeyword());
        List<ProductListResponse> products = productListDao.select(param);
        if (products.size() == PAGE_SIZE + 1) {
            products.remove(PAGE_SIZE);
            isEnd = false;
        }
        return new NoOffsetPageResponse(products, isEnd);
    }

    /**
     * 어드민용 전체 상품 조회
     *
     * @param page 페이지
     * @return 상품 리스트
     */
    @Transactional(readOnly = true)
    public NoOffsetPageResponse getAllList(int page) {
        boolean isEnd = true;
        List<ProductListResponse> products = productListDao.selectAll(page);
        if (products.size() == PAGE_SIZE + 1) {
            products.remove(PAGE_SIZE);
            isEnd = false;
        }
        return new NoOffsetPageResponse(products, isEnd);
    }

    /**
     * 세일 상품 목록 조회
     */
    @Transactional(readOnly = true)
    public NoOffsetPageResponse getSaleList(SaleProductListRequest request) {
        List<Long> products = productListDao.getSaleProductPageList(request);
        if (products.size() == PAGE_SIZE + 1) {
            Long nextSeq = products.get(PAGE_SIZE);
            products.remove(PAGE_SIZE);
            List<ProductListResponse> productInfos = productListDao.getProductInfo(products,
                    ProductSortType.NEWEST.getCode());
            return new NoOffsetPageResponse(productInfos, false, nextSeq);
        } else {
            List<ProductListResponse> productInfos = productListDao.getProductInfo(products,
                    ProductSortType.NEWEST.getCode());
            return new NoOffsetPageResponse(productInfos, true);
        }
    }

    /**
     * 랭킹 조회
     */
    @Cacheable("ranking")
    public ProductRankingResponse getRankingProducts() {
        List<Long> productsPageSeqList = productListDao.getProductsPageSeqList(
                new ProductListRequest(ProductSortType.HOTTEST.getCode()));
        List<ProductListResponse> productInfo = productListDao.getProductInfo(productsPageSeqList,
                ProductSortType.HOTTEST.getCode());
        return new ProductRankingResponse(productInfo);
    }

    /**
     * 캐시 최신화
     */
    @CachePut(value = "ranking")
    public ProductRankingResponse cacheUpdate() {
        List<Long> productsPageSeqList = productListDao.getProductsPageSeqList(
                new ProductListRequest(ProductSortType.HOTTEST.getCode()));
        List<ProductListResponse> productInfo = productListDao.getProductInfo(productsPageSeqList,
                ProductSortType.HOTTEST.getCode());
        return new ProductRankingResponse(productInfo);
    }
}
