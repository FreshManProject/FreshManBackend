package com.freshman.freshmanbackend.global.auth.controller;

import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;
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

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        //쿠키에서 리프레시토큰 꺼내기
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh_token")) {
                refreshToken = cookie.getValue();
            }
        }

        //토큰 존재 여부 확인
        if (refreshToken == null) {
            return new ResponseEntity<>("refresh token not exist", HttpStatus.BAD_REQUEST);
        }

        //만료되었나 확인
        try{
            jwtUtil.isExpired(refreshToken);
        }catch(ExpiredJwtException e){
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        //토큰이 리프레시토큰인가?
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh_token")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String oauth2Id = jwtUtil.getOauth2Id(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        //로그아웃 되지 않은 리프레시토큰인가?
        if (!redisRefreshTokenService.ifRefreshTokenExists(oauth2Id,refreshToken)) return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);

        String newAccessToken = jwtUtil.createJwt("access_token", oauth2Id, role, 600000L);
        String newRefreshToken = jwtUtil.createJwt("refresh_token", oauth2Id, role, 86400000L);

        redisRefreshTokenService.removeRefreshToken(oauth2Id);
        redisRefreshTokenService.saveRefreshToken(oauth2Id,newRefreshToken);

        response.setHeader("access_token", newAccessToken);
        response.addCookie(createCookie("refresh_token", newRefreshToken));
        return ResponseEntity.ok(new SuccessResponse());
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
