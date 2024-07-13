package com.freshman.freshmanbackend.global.auth.service;

import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.redis.service.RedisRefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * JWT 재발급을 처리하는 서비스
 */
@RequiredArgsConstructor
@Service
public class JwtReissueService {
    private JwtUtil jwtUtil;
    private RedisRefreshTokenService redisRefreshTokenService;

    public Tokens reissueTokens(String refreshToken) {
        // 토큰 존재 여부 확인
        if (refreshToken == null) {
            throw new IllegalArgumentException("Refresh token not exist");
        }

        // 만료되었나 확인
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        // 토큰이 리프레시 토큰인가?
        String category = jwtUtil.getCategory(refreshToken);
        if (!"refresh_token".equals(category)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String oauth2Id = jwtUtil.getOauth2Id(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // 로그아웃 되지 않은 리프레시 토큰인가?
        if (!redisRefreshTokenService.ifRefreshTokenExists(oauth2Id, refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.createJwt("access_token", oauth2Id, role, 600000L);
        String newRefreshToken = jwtUtil.createJwt("refresh_token", oauth2Id, role, 86400000L);

        redisRefreshTokenService.removeRefreshToken(oauth2Id);
        redisRefreshTokenService.saveRefreshToken(oauth2Id, newRefreshToken);

        return new Tokens(newAccessToken, newRefreshToken);
    }

    public static class Tokens{
        private final String accessToken;
        private final String refreshToken;

        public Tokens(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

    }
}
