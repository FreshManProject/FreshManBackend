package com.freshman.freshmanbackend.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductReviewResponse {
    private Long reviewSeq;
    private String userName;
    private String content;
    private Byte score;
    private String imagePath;
    private LocalDateTime createdAt;
}
