package com.freshman.freshmanbackend.global.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 성공 공통 응답 형식
 *
 * @author 송병선
 */
@Getter
public class SuccessResponse {
  private final int status = HttpStatus.OK.value();
  private final String message = "success";
}
