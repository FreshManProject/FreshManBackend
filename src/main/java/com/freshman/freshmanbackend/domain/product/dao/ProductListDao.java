package com.freshman.freshmanbackend.domain.product.dao;

import com.freshman.freshmanbackend.domain.product.domain.QProduct;
import com.freshman.freshmanbackend.domain.product.domain.QProductLike;
import com.freshman.freshmanbackend.domain.product.domain.QProductSale;
import com.freshman.freshmanbackend.domain.product.domain.enums.ProductSortType;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductSearchRequest;
import com.freshman.freshmanbackend.domain.product.dto.request.SaleProductListRequest;
import com.freshman.freshmanbackend.domain.product.dto.response.ProductListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 상품 목록 조회 DAO
 */
@Repository
@RequiredArgsConstructor
public class ProductListDao {
    private static final int PAGE_SIZE = 10;
    private final JPAQueryFactory queryFactory;

    /**
     * 어드민용 상품 전체 목록 조회
     *
     * @param page
     * @return
     */
    public List<ProductListResponse> selectAll(int page) {
        QProduct product = QProduct.product;
        QProductSale sale = QProductSale.productSale;
        LocalDateTime curTime = LocalDateTime.now();

        return queryFactory.select(getProjection())
                .from(product)
                .leftJoin(sale)
                .on(sale.productSeq.eq(product.productSeq),
                        sale.saleStartAt.loe(curTime).and(sale.saleEndAt.goe(curTime)))
                .offset(PAGE_SIZE * page)
                .limit((PAGE_SIZE) + 1)
                .fetch();
    }

    /**
     * 상품 아이디만 조회
     *
     * @param param
     * @return
     */
    public List<Long> getProductsPageSeqList(ProductListRequest param) {
        QProduct product = QProduct.product;
        return queryFactory.select(product.productSeq)
                .from(product)
                .where(getCondition(param))
                .orderBy(getOrder(param.getSort()))
                .limit(PAGE_SIZE + 1)
                .fetch();
    }

    /**
     * 세일 상품 아이디 목록 조회
     *
     * @param request
     * @return
     */
    public List<Long> getSaleProductPageList(SaleProductListRequest request) {
        QProductSale sale = QProductSale.productSale;

        return queryFactory.select(sale.productSeq)
                .from(sale)
                .where(getSaleCondition(request))
                .orderBy(sale.productSeq.desc())
                .limit(PAGE_SIZE + 1)
                .fetch();
    }

    /**
     * 상품 정보 조회
     *
     * @param productSeqList
     * @param sort
     * @return
     */
    public List<ProductListResponse> getProductInfo(List<Long> productSeqList, String sort) {
        QProduct product = QProduct.product;
        QProductSale sale = QProductSale.productSale;
        QProductLike productLike = QProductLike.productLike;
        LocalDateTime curTime = LocalDateTime.now();

        return queryFactory.select(getProjection())
                .from(product)
                .leftJoin(sale)
                .on(sale.productSeq.eq(product.productSeq),
                        sale.saleStartAt.loe(curTime).and(sale.saleEndAt.goe(curTime)))
                .leftJoin(productLike)
                .on(productLike.productLikeKey.product.productSeq.eq(product.productSeq))
                .where(product.productSeq.in(productSeqList))
                .orderBy(getOrder(sort))
                .fetch();
    }

    /**
     * 상품 검색 조회
     *
     * @param param 요청 파라미터
     * @return 상품 목록
     */
    public List<ProductListResponse> select(ProductSearchRequest param) {
        QProduct product = QProduct.product;
        QProductSale sale = QProductSale.productSale;
        QProductLike productLike = QProductLike.productLike;
        LocalDateTime curTime = LocalDateTime.now();

        return queryFactory.select(getProjection())
                .from(product)
                .leftJoin(sale)
                .on(sale.productSeq.eq(product.productSeq))
                .leftJoin(productLike)
                .on(productLike.productLikeKey.product.productSeq.eq(product.productSeq))
                .where(getCondition(param))
                .orderBy(getOrder(param.getSort()))
                .offset(PAGE_SIZE * param.getPage())
                .limit((PAGE_SIZE) + 1)
                .fetch();
    }

    /**
     * 상품 목록 조회 조건 반환
     */
    private BooleanBuilder getCondition(ProductListRequest param) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QProduct product = QProduct.product;

        // 유효여부
        booleanBuilder.and(product.valid.eq(Boolean.TRUE));
        // 카테고리
        if (param.getCategorySeq() != null) {
            booleanBuilder.and(product.category.categorySeq.eq(param.getCategorySeq()));
        }

        //최신순 NoOffset 조건
        if (StringUtils.isBlank(param.getSort())) {
            System.out.println(param.getNextSeq());
            if (param.getNextSeq() != null) {
                booleanBuilder.and(product.productSeq.loe(param.getNextSeq()));
            }
        }

        //최신순 NoOffset 조건
        if (StringUtils.equals(param.getSort(), ProductSortType.NEWEST.getCode())) {
            if (param.getNextSeq() != null) {
                booleanBuilder.and(product.productSeq.loe(param.getNextSeq()));
            }
        } else if (StringUtils.equals(param.getSort(), ProductSortType.HIGHEST.getCode())) {//높은 가격 NoOffset 조건
            if (param.getNextPrice() != null && param.getNextSeq() != null) {
                booleanBuilder.and(product.price.eq(param.getNextPrice()));
                booleanBuilder.and(product.productSeq.loe(param.getNextSeq()));
                booleanBuilder.or(product.price.lt(param.getNextPrice()));
            }
        } else if (StringUtils.equals(param.getSort(), ProductSortType.LOWEST.getCode())) {//낮은 가격 순 NoOffset 조건
            if (param.getNextPrice() != null && param.getNextSeq() != null) {
                booleanBuilder.and(product.price.eq(param.getNextPrice()));
                booleanBuilder.and(product.productSeq.loe(param.getNextSeq()));
                booleanBuilder.or(product.price.gt(param.getLowPrice()));
            }
        } else if (StringUtils.equals(param.getSort(), ProductSortType.HOTTEST.getCode())) { //인기순 NoOffset 조건
            if (param.getNextOrderCount() != null && param.getNextSeq() != null) {
                booleanBuilder.and(product.orderCount.eq(param.getNextOrderCount()));
                booleanBuilder.and(product.productSeq.loe(param.getNextSeq()));
                booleanBuilder.or(product.orderCount.lt(param.getNextOrderCount()));
            }
        }

        // 낮은 가격 제한
        if (param.getLowPrice() != null) {
            booleanBuilder.and(product.price.goe(param.getLowPrice()));
        }

        // 높은 가격 제한
        if (param.getHighPrice() != null) {
            booleanBuilder.and(product.price.loe(param.getHighPrice()));
        }

        return booleanBuilder;
    }

    private BooleanBuilder getSaleCondition(SaleProductListRequest request) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        LocalDateTime now = LocalDateTime.now();
        QProductSale sale = QProductSale.productSale;

        booleanBuilder.and(sale.saleStartAt.loe(now));
        booleanBuilder.and(sale.saleEndAt.gt(now));

        if (request.getNextSeq() != null) {
            booleanBuilder.and(sale.productSeq.loe(request.getNextSeq()));
        }

        return booleanBuilder;
    }

    /**
     * 상품 검색 조회 조건 반환
     */
    private BooleanBuilder getCondition(ProductSearchRequest param) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QProduct product = QProduct.product;

        // 유효여부
        booleanBuilder.and(product.valid.eq(Boolean.TRUE));
        // 카테고리
        if (param.getCategorySeq() != null) {
            booleanBuilder.and(product.category.categorySeq.eq(param.getCategorySeq()));
        }

        // 검색 키워드
        if (StringUtils.isNotBlank(param.getKeyword())) {
            booleanBuilder.and(product.name.like("%" + param.getKeyword() + "%"));
        }
        // 낮은 가격
        if (param.getLowPrice() != null) {
            booleanBuilder.and(product.price.goe(param.getLowPrice()));
        }
        // 높은 가격
        if (param.getHighPrice() != null) {
            booleanBuilder.and(product.price.loe(param.getHighPrice()));
        }

        return booleanBuilder;
    }

    private OrderSpecifier[] getOrder(String sort) {
        QProduct product = QProduct.product;
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (StringUtils.isBlank(sort)) {
            orderSpecifiers.add(product.productSeq.desc());
            return orderSpecifiers.toArray(OrderSpecifier[]::new);
        }

        if (StringUtils.equals(sort, ProductSortType.NEWEST.getCode())) {
            orderSpecifiers.add(product.productSeq.desc());
            return orderSpecifiers.toArray(OrderSpecifier[]::new);
        } else if (StringUtils.equals(sort, ProductSortType.HIGHEST.getCode())) {
            orderSpecifiers.add(product.price.desc());
            orderSpecifiers.add(product.productSeq.desc());
            return orderSpecifiers.toArray(OrderSpecifier[]::new);
        } else if (StringUtils.equals(sort, ProductSortType.LOWEST.getCode())) {//낮은 가격순
            orderSpecifiers.add(product.price.asc());
            orderSpecifiers.add(product.productSeq.desc());
            return orderSpecifiers.toArray(OrderSpecifier[]::new);
        } else { // 인기순
            orderSpecifiers.add(product.orderCount.desc());
            orderSpecifiers.add(product.productSeq.desc());
            return orderSpecifiers.toArray(OrderSpecifier[]::new);
        }
    }

    private ConstructorExpression<ProductListResponse> getProjection() {
        QProduct product = QProduct.product;
        QProductSale sale = QProductSale.productSale;
        QProductLike productLike = QProductLike.productLike;
        return Projections.constructor(ProductListResponse.class, product.productSeq, product.name, product.price,
                sale.salePrice, product.brand, product.thumbnailImage, productLike.isNotNull(), product.orderCount);
    }
}
