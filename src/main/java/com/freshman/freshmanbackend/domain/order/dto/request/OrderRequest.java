package com.freshman.freshmanbackend.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderRequest {
    private Long productSeq;
    private Integer orderCount;
}
