package com.freshman.freshmanbackend.domain.question.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 문의 응답 객체
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductQuestionResponse {
  private String memberName;
  private String content;
  private LocalDate postedDate;
}
