package com.freshman.freshmanbackend.global.common.exception.handler;


import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import com.freshman.freshmanbackend.global.common.response.ErrorResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleValidationException(ValidationException e, WebRequest request) {
    log.info("Exception: " + e.getClass().getSimpleName() + "(" + e.getLocalizedMessage() + ")");
    final Locale locale = request.getLocale();
    String message;
    message = messageSource.getMessage(e.getMessage(), null, locale);

    return ResponseEntity.ok(new ErrorResponse(e.getStatus(), message));
  }
}
