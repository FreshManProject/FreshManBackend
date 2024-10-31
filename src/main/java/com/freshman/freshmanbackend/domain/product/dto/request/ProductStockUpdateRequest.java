package com.freshman.freshmanbackend.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductStockUpdateRequest {
    /**
     * 재고 증가(increase)/감소(decrease)/설정(set)
     */
    private String type;
    /**
     * 상품 아이디
     */
    private Long productSeq;
    /**
     * 재고 변동 값
     */
    private Integer count;
}
