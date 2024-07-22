package com.freshman.freshmanbackend.global.auth.util;

import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Iterator;

import lombok.experimental.UtilityClass;

/**
 * 현재 로그인한 유저의 정보를 처리하는 유틸 클래스
 */
@UtilityClass
public class AuthMemberUtils {
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public Long getMemberSeq() {
    CustomOauth2User oauth2User = (CustomOauth2User) getAuthentication().getPrincipal();
    return oauth2User.getMemberSeq();
  }

  public String getUserOauth2Id() {
    CustomOauth2User oauth2User = (CustomOauth2User) getAuthentication().getPrincipal();
    return oauth2User.getName();
  }

  public String getUserRole() {
    Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    return auth.getAuthority();
  }
}
