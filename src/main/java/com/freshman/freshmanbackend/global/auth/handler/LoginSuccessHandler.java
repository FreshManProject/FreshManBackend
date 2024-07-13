package com.freshman.freshmanbackend.global.auth.handler;

import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.utils.HttpUtils;
import com.freshman.freshmanbackend.global.redis.service.RedisRefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Oauth2 로그인 성공 핸들러
 */
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RedisRefreshTokenService redisRefreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2User customUserDetails = (CustomOauth2User) authentication.getPrincipal();
        String oauth2Id = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt("access_token",oauth2Id, role, 600000L);
        String refreshToken = jwtUtil.createJwt("refresh_token",oauth2Id, role, 864000000L);

        redisRefreshTokenService.removeRefreshToken(oauth2Id);
        redisRefreshTokenService.saveRefreshToken(refreshToken,oauth2Id);

        response.setHeader("access_token", accessToken);
        response.addCookie(HttpUtils.createCookie("refresh_token", refreshToken));
        response.setStatus(HttpStatus.OK.value());
    }
}
