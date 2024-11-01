package com.freshman.freshmanbackend.domain.product.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 랭킹 응답
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductRankingResponse {
    private List<ProductListResponse> products;
}
