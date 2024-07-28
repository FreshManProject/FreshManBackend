package com.freshman.freshmanbackend.domain.question.request;

import com.freshman.freshmanbackend.domain.question.domain.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 문의 생성 요청 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntryRequest {
  private String title;
  private String content;
  private String type;

  public Question toQuestionEntity() {
    return new Question(title, content, type);
  }
}
