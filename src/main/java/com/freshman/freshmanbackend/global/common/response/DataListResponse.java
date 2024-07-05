package com.freshman.freshmanbackend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

/**
 * 단일 데이터와 목록 조회 공통 응답 형식
 *
 * @author 송병선
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataListResponse extends SuccessResponse {
  private final Object data;
  private final List<?> list;
  private int count;

  public DataListResponse(Object data, List<?> list) {
    this.data = data;
    this.list = list;
    if (this.list != null && !this.list.isEmpty()) {
      this.count = this.list.size();
    }
  }
}
