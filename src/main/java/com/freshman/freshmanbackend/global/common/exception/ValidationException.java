package com.freshman.freshmanbackend.global.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 유효성 검사 에러
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

  private int status = HttpStatus.BAD_REQUEST.value();

  public ValidationException(String message) {
    super("." + message);
  }

  @Override
  public String getMessage() {
    return "validation" + super.getMessage();
  }
}
