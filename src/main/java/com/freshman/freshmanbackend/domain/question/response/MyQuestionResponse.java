package com.freshman.freshmanbackend.domain.question.response;

import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 내 질문 응답
 */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MyQuestionResponse {
  private Long questionSeq;
  private String memberName;
  private String content;
  private String image;
  private LocalDateTime postedDate;
  private Boolean isAnswered;
  private String productName;
  private String productImage;
  private Long productSeq;

  public MyQuestionResponse(Long questionSeq, String content, String image, LocalDateTime postedDate, Boolean isAnswered, String productName, String productImage, Long productSeq) {
    this.questionSeq = questionSeq;
    this.content = content;
    this.image = image;
    this.postedDate = postedDate;
    this.isAnswered = isAnswered;
    this.productName = productName;
    this.productImage = productImage;
    this.productSeq = productSeq;
  }
}
