package com.freshman.freshmanbackend.domain.product.service.query.product;

import com.freshman.freshmanbackend.domain.product.dao.ProductListDao;
import com.freshman.freshmanbackend.domain.product.domain.enums.ProductSortType;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductSearchRequest;
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
        Boolean isEnd = true;
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
        Boolean isEnd = true;
        // 최근 검색어 등록
        searchLogService.entry(param.getKeyword());
        List<ProductListResponse> products = productListDao.select(param);
        if (products.size() == PAGE_SIZE + 1) {
            products.remove(PAGE_SIZE);
            isEnd = false;
        }
        return new NoOffsetPageResponse(products, isEnd);
    }

    @Transactional(readOnly = true)
    public NoOffsetPageResponse getAllList(int page) {
        Boolean isEnd = true;
        List<ProductListResponse> products = productListDao.selectAll(page);
        if (products.size() == PAGE_SIZE + 1) {
            products.remove(PAGE_SIZE);
            isEnd = false;
        }
        return new NoOffsetPageResponse(products, isEnd);
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

    @CachePut(value = "ranking")
    public ProductRankingResponse cacheUpdate() {
        List<Long> productsPageSeqList = productListDao.getProductsPageSeqList(
                new ProductListRequest(ProductSortType.HOTTEST.getCode()));
        List<ProductListResponse> productInfo = productListDao.getProductInfo(productsPageSeqList,
                ProductSortType.HOTTEST.getCode());
        return new ProductRankingResponse(productInfo);
    }
}
