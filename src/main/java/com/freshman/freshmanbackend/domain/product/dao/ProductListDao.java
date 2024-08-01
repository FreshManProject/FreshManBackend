package com.freshman.freshmanbackend.domain.product.dao;

import com.freshman.freshmanbackend.domain.product.domain.QProduct;
import com.freshman.freshmanbackend.domain.product.domain.QProductSale;
import com.freshman.freshmanbackend.domain.product.domain.enums.ProductSortType;
import com.freshman.freshmanbackend.domain.product.request.ProductListRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSearchRequest;
import com.freshman.freshmanbackend.domain.product.response.ProductListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 상품 목록 조회 DAO
 */
@Repository
@RequiredArgsConstructor
public class ProductListDao {

  private final JPAQueryFactory queryFactory;

  /**
   * 상품 목록 조회
   *
   * @param param 요청 파라미터
   * @return 상품 목록
   */
  public List<ProductListResponse> select(ProductListRequest param) {
    QProduct product = QProduct.product;
    QProductSale sale = QProductSale.productSale;
    LocalDateTime curTime = LocalDateTime.now();

    return queryFactory.select(getProjection())
                       .from(product)
                       .leftJoin(sale)
                       .on(sale.productSeq.eq(product.productSeq),
                           sale.saleStartAt.loe(curTime).and(sale.saleEndAt.goe(curTime)))
                       .where(getCondition(param))
                       .orderBy(getOrder(param.getSort()))
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
    LocalDateTime curTime = LocalDateTime.now();

    return queryFactory.select(getProjection())
                       .from(product)
                       .leftJoin(sale)
                       .on(sale.productSeq.eq(product.productSeq),
                           sale.saleStartAt.loe(curTime).and(sale.saleEndAt.goe(curTime)))
                       .where(getCondition(param))
                       .orderBy(getOrder(param.getSort()))
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
    booleanBuilder.and(product.category.categorySeq.eq(param.getCategorySeq()));

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

  private OrderSpecifier<?> getOrder(String sort) {
    QProduct product = QProduct.product;

    if (StringUtils.isBlank(sort)) {
      return product.createdAt.desc();
    }

    if (StringUtils.equals(sort, ProductSortType.NEWEST.getCode())) {
      return product.createdAt.desc();
    } else if (StringUtils.equals(sort, ProductSortType.HIGHEST.getCode())) {
      return product.price.desc();
    } else {
      return product.price.asc();
    }
  }

  private ConstructorExpression<ProductListResponse> getProjection() {
    QProduct product = QProduct.product;
    QProductSale sale = QProductSale.productSale;
    return Projections.constructor(ProductListResponse.class, product.productSeq, product.name, product.price,
        sale.salePrice, product.brand);
  }
}
