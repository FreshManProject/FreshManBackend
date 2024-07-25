package com.freshman.freshmanbackend.batch.job;

import com.freshman.freshmanbackend.domain.product.domain.SearchLog;
import com.freshman.freshmanbackend.global.common.utils.DateTimeUtils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;

import static com.freshman.freshmanbackend.domain.product.service.SearchLogService.SEARCH_LOG_PREFIX;

/**
 * 유효기간이 지난 최근 검색어 삭제 작업
 *
 * @author 송병선
 */
@Component
@RequiredArgsConstructor
public class SearchKeywordDeleteJob {

  private final RedisTemplate<String, SearchLog> redisTemplate;

  /**
   * 유효기간이 지난 최근 검색어 삭제
   */
  @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Seoul")
  public void execute() {
    // SEARCH_LOG_PREFIX 접두사로 시작하는 모든 키 조회
    Set<String> keys = redisTemplate.keys(SEARCH_LOG_PREFIX + "*");
    if (keys == null || keys.isEmpty()) {
      return;
    }

    LocalDateTime curTime = LocalDateTime.now();
    // 유효기간(7일)이 지난 최근 검색어 삭제
    for (String key : keys) {
      List<SearchLog> list = redisTemplate.opsForList().range(key, 0, -1);
      if (list == null || list.isEmpty()) {
        continue;
      }

      OptionalInt index = IntStream.range(0, list.size()).filter(i -> {
        LocalDateTime createdAt = DateTimeUtils.convertToDateTime(list.get(i).getCreatedAt());
        return createdAt.plusDays(7).isBefore(curTime);
      }).findFirst();

      if (index.isPresent()) {
        int keywordIndex = index.getAsInt();
        if (keywordIndex == 0) {
          // 첫 번째 검색어가 유효기간이 지났다면 전체 삭제
          redisTemplate.delete(key);
        } else {
          // 유효기간이 지난 첫 번째 index 이후 검색어 삭제
          redisTemplate.opsForList().trim(key, 0, keywordIndex - 1);
        }
      }
    }
  }
}
