package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.global.common.utils.DateTimeUtils;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 최근 검색어 저장 객체
 *
 * 
 */
@Getter
@NoArgsConstructor
public class SearchLog {

  /**
   * 검색 키워드명
   */
  private String keyword;
  /**
   * 검색일시
   */
  private String createdAt;

  public SearchLog(String keyword) {
    this.keyword = keyword;
    this.createdAt = DateTimeUtils.convertToString(LocalDateTime.now(), DateTimeUtils.DEFAULT_DATETIME);
  }
}
