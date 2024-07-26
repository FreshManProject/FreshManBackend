package com.freshman.freshmanbackend.global.auth.config;

import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.global.auth.filter.JwtFilter;
import com.freshman.freshmanbackend.global.auth.filter.JwtLogoutFilter;
import com.freshman.freshmanbackend.global.auth.handler.LoginSuccessHandler;
import com.freshman.freshmanbackend.global.auth.service.CustomOAuth2UserService;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.redis.service.RedisRefreshTokenService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 스프링 시큐리티 설정
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
  private final CustomOAuth2UserService customOAuth2UserService;
  private final LoginSuccessHandler loginSuccessHandler;
  private final RedisRefreshTokenService redisRefreshTokenService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil, MemberRepository memberRepository)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setMaxAge(3600L);

            configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
            configuration.setExposedHeaders(Collections.singletonList("Authorization"));

            return configuration;
          }
        }))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .oauth2Login((oauth2) -> oauth2.userInfoEndpoint(
                                           (userInfoEndpointConfig) -> userInfoEndpointConfig.userService(customOAuth2UserService))
                                       .successHandler(loginSuccessHandler))
        .addFilterBefore(new JwtFilter(jwtUtil, memberRepository), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JwtLogoutFilter(jwtUtil, redisRefreshTokenService), LogoutFilter.class)
        .authorizeHttpRequests(
            (auth) -> auth.requestMatchers("/reissue", "/products/**").permitAll().anyRequest().authenticated())
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }
}
