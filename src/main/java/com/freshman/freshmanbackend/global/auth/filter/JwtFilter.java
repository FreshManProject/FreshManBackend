package com.freshman.freshmanbackend.global.auth.filter;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;
import com.freshman.freshmanbackend.global.auth.dto.OauthUserDto;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT 필터
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final MemberRepository memberRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String accessToken = request.getHeader("Authorization");
    if (accessToken == null || !accessToken.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    accessToken = accessToken.substring(7);

    try {
      jwtUtil.isExpired(accessToken);
    } catch (ExpiredJwtException e) {

      PrintWriter writer = response.getWriter();
      writer.print("access token expired");

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String category = jwtUtil.getCategory(accessToken);
    if (!category.equals("access_token")) {
      PrintWriter writer = response.getWriter();
      writer.print("invalid access token");

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    //토큰에서 oauth2Id과 role 획득
    String oauth2Id = jwtUtil.getOauth2Id(accessToken);
    String role = jwtUtil.getRole(accessToken);
    Member member = memberRepository.findByOauth2Id(oauth2Id);

    //userDTO를 생성하여 값 set
    OauthUserDto userDTO = OauthUserDto.fromMember(member, role);

    //UserDetails에 회원 정보 객체 담기
    CustomOauth2User customOAuth2User = new CustomOauth2User(userDTO);

    //스프링 시큐리티 인증 토큰 생성
    Authentication authToken =
        new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
    //세션에 사용자 등록
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
