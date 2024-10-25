package com.freshman.freshmanbackend.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 후기 댓글 응답
 */
@NoArgsConstructor
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewCommentResponse {
    private Long commentSeq;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}
