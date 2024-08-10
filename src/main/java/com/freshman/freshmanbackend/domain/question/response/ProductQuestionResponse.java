package com.freshman.freshmanbackend.domain.question.response;

import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class ProductQuestionResponse {
  private Long questionSeq;
  private String memberName;
  private String content;
  private String image;
  private Boolean isAnswered;
  private LocalDate postedDate;

  public static ProductQuestionResponse fromQuestion(Question question) {
    return ProductQuestionResponse.builder()
                                  .questionSeq(question.getQuestionSeq())
                                  .memberName(AuthMemberUtils.getOauthUserDto().getName())
                                  .content(question.getContent())
                                  .image(question.getImage())
                                  .isAnswered(question.getIsAnswered())
                                  .postedDate(question.getCreatedAt().toLocalDate())
                                  .build();
  }
}
