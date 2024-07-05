package com.freshman.freshmanbackend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

/**
 * 단일 데이터 공통 응답 형식
 *
 * @author 송병선
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse extends SuccessResponse {

  private final Object data;

  public DataResponse(Object data) {
    this.data = data;
  }
}