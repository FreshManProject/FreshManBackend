package com.freshman.freshmanbackend.global.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * 로그인 사용자 정보
 */
@RequiredArgsConstructor
public class CustomOauth2User implements OAuth2User {
  private final OauthUserDto userDto;

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();

    collection.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {

        return userDto.getRole();
      }
    });

    return collection;
  }

  public Long getMemberSeq() {
    return userDto.getMemberSeq();
  }

  @Override
  public String getName() {
    return userDto.getOauth2Id();
  }

  public OauthUserDto getOathUserDto() {
    return userDto;
  }
}
