package com.freshman.freshmanbackend.global.redis.config;

import com.freshman.freshmanbackend.domain.product.domain.SearchLog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 레디스 설정
 */
@Configuration
public class RedisConfig {
  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  /**
   * 상품 최근 검색어 로그 템플릿
   */
  @Bean
  public RedisTemplate<String, SearchLog> SearchLogRedis() {
    RedisTemplate<String, SearchLog> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(SearchLog.class));
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SearchLog.class));
    return redisTemplate;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }
}
