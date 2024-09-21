package com.freshman.freshmanbackend.domain.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
