package com.freshman.freshmanbackend.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 목록 조회 요청
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductListRequest {

    /**
     * 카테고리 일련번호
     */
    private Long categorySeq;
    /**
     * 낮은 가격
     */
    private Integer lowPrice;
    /**
     * 높은 가격
     */
    private Integer highPrice;
    /**
     * 정렬
     */
    private String sort;

    /**
     * 읽기 시작할 productSeq
     */
    private Long nextSeq;
    /**
     * 읽기 시작할 price
     */
    private Long nextPrice;
    /**
     * 읽기 시작할 판매량
     */
    private Integer nextOrderCount;


    public ProductListRequest(String sort) {
        this.sort = sort;
    }
}
