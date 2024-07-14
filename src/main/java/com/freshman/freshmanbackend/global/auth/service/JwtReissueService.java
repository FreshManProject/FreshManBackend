package com.freshman.freshmanbackend.global.auth.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.freshman.freshmanbackend.global.auth.dto.Tokens;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import com.freshman.freshmanbackend.global.redis.service.RedisRefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * JWT 재발급을 처리하는 서비스
 */
@RequiredArgsConstructor
@Service
public class JwtReissueService {
    private final JwtUtil jwtUtil;
    private final RedisRefreshTokenService redisRefreshTokenService;

    public Tokens reissueTokens(String refreshToken) {
        checkRefreshTokenValidity(refreshToken);
        String oauth2Id = jwtUtil.getOauth2Id(refreshToken);
        String role = jwtUtil.getRole(refreshToken);
        return getNewTokens(refreshToken, oauth2Id, role);
    }

    //새 액세스토큰과 리프레시 토큰 발급
    private Tokens getNewTokens(String refreshToken, String oauth2Id, String role) {
        // 로그아웃 되지 않은 리프레시 토큰인가?
        if (!redisRefreshTokenService.ifRefreshTokenExists(oauth2Id, refreshToken)) {
            throw new ValidationException("auth.invalid_refresh_token");
        }
        String newAccessToken = jwtUtil.createJwt("access_token", oauth2Id, role, 600000L);
        String newRefreshToken = jwtUtil.createJwt("refresh_token", oauth2Id, role, 86400000L);

        redisRefreshTokenService.removeRefreshToken(oauth2Id);
        redisRefreshTokenService.saveRefreshToken(oauth2Id, newRefreshToken);

        return new Tokens(newAccessToken, newRefreshToken);
    }

    //리프레시 토큰의 유효성 확인
    private void checkRefreshTokenValidity(String refreshToken) {
        // 토큰 존재 여부 확인
        if (refreshToken == null) {
            throw new ValidationException("auth.refresh_token_not_exist");
        }

        // 만료되었나 확인
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ValidationException("auth.invalid_refresh_token");
        }

        // 토큰이 리프레시 토큰인가?
        String category = jwtUtil.getCategory(refreshToken);
        if (!"refresh_token".equals(category)) {
            throw new ValidationException("auth.invalid_refresh_token");
        }
    }
}
