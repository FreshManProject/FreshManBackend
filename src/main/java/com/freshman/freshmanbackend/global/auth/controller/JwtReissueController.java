package com.freshman.freshmanbackend.global.auth.controller;

import com.freshman.freshmanbackend.global.auth.service.JwtReissueService;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;
import com.freshman.freshmanbackend.global.common.utils.HttpUtils;
import com.freshman.freshmanbackend.global.redis.service.RedisRefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * JWT 토큰을 재발급 하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class JwtReissueController {
    private final JwtUtil jwtUtil;
    private final RedisRefreshTokenService redisRefreshTokenService;
    private final JwtReissueService jwtReissueService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = HttpUtils.getCookieValueByCookieKey(request, "refresh_token");
        JwtReissueService.Tokens tokens = jwtReissueService.reissueTokens(refreshToken);
        response.setHeader("access_token", tokens.accessToken());
        response.addCookie(HttpUtils.createCookie("refresh_token", tokens.refreshToken()));
        return ResponseEntity.ok(new SuccessResponse());
    }
}
