package com.freshman.freshmanbackend.global.redis.repository;

import com.freshman.freshmanbackend.global.redis.domain.RedisRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 레디스 refresh token 리포지토리
 */
public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, String> {
    Optional<RedisRefreshToken> findByRefreshToken(String refreshToken);
}
