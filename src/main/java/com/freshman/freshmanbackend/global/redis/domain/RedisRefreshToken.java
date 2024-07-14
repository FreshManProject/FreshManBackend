package com.freshman.freshmanbackend.global.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 리프레시 토큰 저장 객체
 */
@AllArgsConstructor
@Getter
@RedisHash(value = "refreshToken",timeToLive = 864000000L)
public class RedisRefreshToken {
    @Id
    private String oauth2Id;

    private String refreshToken;
}
