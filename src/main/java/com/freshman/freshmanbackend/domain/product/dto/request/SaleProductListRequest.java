package com.freshman.freshmanbackend.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaleProductListRequest {
    /**
     * 다음에 읽어올 productSeq
     */
    private Long nextSeq;
}
