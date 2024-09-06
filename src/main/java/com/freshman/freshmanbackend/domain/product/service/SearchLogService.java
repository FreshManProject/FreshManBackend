package com.freshman.freshmanbackend.domain.product.service;

import com.freshman.freshmanbackend.domain.product.domain.SearchLog;
import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 상품 최근 검색어 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class SearchLogService {

  public static final String SEARCH_LOG_PREFIX = "SearchLog";

  private final RedisTemplate<String, SearchLog> redisTemplate;

  /**
   * 최근 검색어 삭제
   *
   * @param index 키워드 인덱스
   */
  public void delete(Long index) {
    String key = SEARCH_LOG_PREFIX + AuthMemberUtils.getMemberSeq();

    if (index == null) {
      // 최근 검색어 전체 삭제
      redisTemplate.delete(key);
    } else {
      // 최근 검색어 삭제
      SearchLog searchLog = redisTemplate.opsForList().index(key, index);
      if (searchLog != null) {
        redisTemplate.opsForList().remove(key, 1, searchLog);
      } else {
        throw new ValidationException("search.keyword.not_found");
      }
    }
  }

  /**
   * 최근 검색어 등록
   *
   * @param keyword 검색 키워드
   */
  public void entry(String keyword) {
    // 검색 키워드가 공백이거나 로그인 상태가 아니면 종료
    if (StringUtils.isBlank(keyword) ||
        !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomOauth2User)) {
      return;
    }

    String key = SEARCH_LOG_PREFIX + AuthMemberUtils.getMemberSeq();

    // Redis 리스트에서 중복 데이터 조회
    SearchLog searchLog = redisTemplate.opsForList()
                                       .range(key, 0, -1)
                                       .stream()
                                       .map(SearchLog.class::cast)
                                       .filter(log -> log.getKeyword().equals(keyword))
                                       .findFirst()
                                       .orElse(null);

    if (searchLog != null) {
      // 중복된 데이터를 맨 앞으로 이동시키기
      redisTemplate.opsForList().remove(key, 1, searchLog);
      redisTemplate.opsForList().leftPush(key, new SearchLog(keyword));
    } else {
      // 중복된 데이터가 없으면 새로운 데이터 추가
      SearchLog log = new SearchLog(keyword);
      Long size = redisTemplate.opsForList().size(key);
      if (size != null && size == 10) {
        // 리스트가 꽉 찼을 경우 오래된 데이터 삭제
        redisTemplate.opsForList().rightPop(key);
      }
      redisTemplate.opsForList().leftPush(key, log);
    }
  }

  /**
   * 최근 검색어 목록 조회
   *
   * @return 최근 검색어 목록
   */
  public List<String> getList() {
    String key = SEARCH_LOG_PREFIX + AuthMemberUtils.getMemberSeq();
    return redisTemplate.opsForList().range(key, 0, 10).stream().map(SearchLog::getKeyword).toList();
  }
}