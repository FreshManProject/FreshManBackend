package com.freshman.freshmanbackend.domain.cart.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class CartListResponse {
    private Long totalElementCount;
    private Integer currentPageElementCount;
    private List<CartInfoResponse> carts;

    public CartListResponse(Long totalCount, List<CartInfoResponse> carts) {
        this.totalElementCount = totalCount;
        this.currentPageElementCount = carts.size();
        this.carts = carts;
    }
}
