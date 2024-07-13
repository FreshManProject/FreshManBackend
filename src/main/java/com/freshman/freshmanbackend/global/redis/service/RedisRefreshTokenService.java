package com.freshman.freshmanbackend.global.redis.service;

import com.freshman.freshmanbackend.global.redis.domain.RedisRefreshToken;
import com.freshman.freshmanbackend.global.redis.repository.RedisRefreshTokenRepository;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 레디스 리프레시 토큰 관련 서비스
 */
@RequiredArgsConstructor
@Service
public class RedisRefreshTokenService {
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Transactional
    public void saveRefreshToken(String refreshToken, String oauth2Id) {
        redisRefreshTokenRepository.save(new RedisRefreshToken(oauth2Id, refreshToken));
    }

    @Transactional
    public void removeRefreshToken(String oauth2Id) {
        redisRefreshTokenRepository.deleteById(oauth2Id);
    }

    @Transactional(readOnly = true)
    public boolean ifRefreshTokenExists(String oauth2Id, String refreshToken) {
        Optional<RedisRefreshToken> tokenOptional = redisRefreshTokenRepository.findById(oauth2Id);
        return tokenOptional.map(redisRefreshToken -> redisRefreshToken.getRefreshToken().equals(refreshToken)).orElse(false);
    }
}
