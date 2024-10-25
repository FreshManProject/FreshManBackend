package com.freshman.freshmanbackend.domain.product.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 후기 수정 요청
 */
@Getter
@Setter
public class ReviewModifyRequest {

    /**
     * 후기 일련번호
     */
    private Long reviewSeq;
    /**
     * 후기 내용
     */
    private String content;
    /**
     * 별점
     */
    private Byte score;

    /**
     * 이미지
     */
    private MultipartFile image;
}
