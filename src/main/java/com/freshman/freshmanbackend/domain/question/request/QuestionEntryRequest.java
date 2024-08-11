package com.freshman.freshmanbackend.domain.question.request;

import com.freshman.freshmanbackend.domain.question.domain.Question;

import org.springframework.web.multipart.MultipartFile;

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
  private String content;
  private String type;
  private MultipartFile image;

  public Question toQuestionEntity() {
    return new Question(content, type);
  }
}
