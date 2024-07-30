package com.freshman.freshmanbackend.domain.question.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 내 질문 응답
 */
@NoArgsConstructor
@Getter
@Setter
public class MyQuestionResponse {
  private String memberName;
  private String content;
  private String image;
  private LocalDate postedDate;
  private Boolean isAnswered;
  private String productName;
  private String productImage;
  private Long productSeq;
}
