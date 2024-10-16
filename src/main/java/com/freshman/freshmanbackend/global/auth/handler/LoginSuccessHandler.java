package com.freshman.freshmanbackend.global.auth.handler;

import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.utils.HttpUtils;
import com.freshman.freshmanbackend.global.redis.service.RedisRefreshTokenService;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Oauth2 로그인 성공 핸들러
 */
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final JwtUtil jwtUtil;
  private final RedisRefreshTokenService redisRefreshTokenService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String oauth2Id = AuthMemberUtils.getUserOauth2Id();
    String role = AuthMemberUtils.getUserRole();

    String accessToken = jwtUtil.createJwt("access_token", oauth2Id, role, 600000L);
    String refreshToken = jwtUtil.createJwt("refresh_token", oauth2Id, role, 864000000L);
    redisRefreshTokenService.removeRefreshToken(oauth2Id);
    redisRefreshTokenService.saveRefreshToken(refreshToken, oauth2Id);

    response.addCookie(HttpUtils.createCookie("refresh_token", refreshToken));
    response.sendRedirect("http://localhost:3000/authorize?access_token=" + accessToken);
  }
}
