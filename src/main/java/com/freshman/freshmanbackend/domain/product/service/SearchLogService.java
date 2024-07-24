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
import java.util.Objects;

import lombok.RequiredArgsConstructor;

/**
 * 상품 최근 검색어 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class SearchLogService {

  private final RedisTemplate<String, SearchLog> redisTemplate;

  /**
   * 최근 검색어 삭제
   *
   * @param keyword 검색 키워드
   */
  public void delete(String keyword) {
    String key = "SearchLog" + AuthMemberUtils.getMemberSeq();

    if (StringUtils.isBlank(keyword)) {
      // 최근 검색어 전체 삭제
      redisTemplate.delete(key);
    } else {
      // 최근 검색어 삭제
      List<SearchLog> list = redisTemplate.opsForList().range(key, 0, -1);
      if (Objects.nonNull(list) && !list.isEmpty()) {
        SearchLog searchLog = list.stream()
                                  .filter(log -> StringUtils.equals(keyword, log.getKeyword()))
                                  .findFirst()
                                  .orElseThrow(() -> new ValidationException("search.keyword.not_found"));

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

    String key = "SearchLog" + AuthMemberUtils.getMemberSeq();

    // Redis 리스트에서 중복 데이터 조회
    SearchLog searchLog = redisTemplate.opsForList()
                                       .range(key, 0, -1)
                                       .stream()
                                       .map(SearchLog.class::cast)
                                       .filter(log -> log.getKeyword().equals(keyword))
                                       .findFirst()
                                       .orElse(null);

    if (Objects.nonNull(searchLog)) {
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
    String key = "SearchLog" + AuthMemberUtils.getMemberSeq();
    return redisTemplate.opsForList().range(key, 0, 10).stream().map(SearchLog::getKeyword).toList();
  }
}