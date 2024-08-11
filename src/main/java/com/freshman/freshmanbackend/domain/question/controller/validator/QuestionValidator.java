package com.freshman.freshmanbackend.domain.question.controller.validator;

import com.freshman.freshmanbackend.domain.question.request.QuestionEntryRequest;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import lombok.experimental.UtilityClass;

/**
 * 문의 요청 검증
 */
@UtilityClass
public class QuestionValidator {

  public void validate(QuestionEntryRequest questionEntryRequest) {
    validateNull(questionEntryRequest.getContent(), "question.content_null");
    validateNull(questionEntryRequest.getType(), "question.type_null");
  }

  private void validateNull(Object obj, String exception) {
    if (obj == null) {
      throw new ValidationException(exception);
    }
  }
}
