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
  private LocalDate postedDate;
  private Boolean isAnswered;
  private String productName;
  private String productImage;
  private Long productSeq;

  public static MyQuestionResponse of(Question question) {
    return MyQuestionResponse.builder()
                             .questionSeq(question.getQuestionSeq())
                             .memberName(AuthMemberUtils.getOauthUserDto().getName())
                             .content(question.getContent())
                             .image(question.getImage())
                             .postedDate(question.getCreatedAt().toLocalDate())
                             .isAnswered(question.getIsAnswered())
                             .productName(question.getProduct().getName())
                             .productSeq(question.getProduct().getProductSeq())
                             .build();
  }
}
