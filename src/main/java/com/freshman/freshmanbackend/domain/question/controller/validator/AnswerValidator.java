package com.freshman.freshmanbackend.domain.question.controller.validator;

import com.freshman.freshmanbackend.domain.question.request.AnswerEntryRequest;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import lombok.experimental.UtilityClass;

/**
 * 답변 검증
 */
@UtilityClass
public class AnswerValidator {
  public void validate(AnswerEntryRequest answerEntryRequest) {
    validateNull(answerEntryRequest.getContent(), "answer.content_null");
  }

  private void validateNull(Object obj, String exception) {
    if (obj == null) {
      throw new ValidationException(exception);
    }
  }
}
