package com.freshman.freshmanbackend.domain.question.response;

import com.freshman.freshmanbackend.domain.question.domain.Answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 답변 응답
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {
  private String content;

  public static AnswerResponse of(Answer answer) {
    return new AnswerResponse(answer.getContent());
  }
}
