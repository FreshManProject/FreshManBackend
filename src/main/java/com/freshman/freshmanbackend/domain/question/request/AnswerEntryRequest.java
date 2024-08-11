package com.freshman.freshmanbackend.domain.question.request;

import com.freshman.freshmanbackend.domain.question.domain.Answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 답변 요청
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntryRequest {
  private String content;

  public Answer toEntity() {
    return new Answer(content);
  }
}
