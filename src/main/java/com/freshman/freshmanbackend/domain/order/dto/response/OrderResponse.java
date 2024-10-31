package com.freshman.freshmanbackend.domain.order.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderResponse {
    private Long orderSeq;
    private Integer orderCount;
    private LocalDateTime orderAt;
    private Long productSeq;
    private String productName;
    private Long totalPrice;
}
