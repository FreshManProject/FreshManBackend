package com.freshman.freshmanbackend.global.auth.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.domain.enums.Role;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;
import com.freshman.freshmanbackend.global.auth.dto.OauthUserDto;
import com.freshman.freshmanbackend.global.auth.response.KakaoResponse;
import com.freshman.freshmanbackend.global.auth.response.NaverResponse;
import com.freshman.freshmanbackend.global.auth.response.Oauth2Response;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * 커스텀 유저 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
    Oauth2Response oauth2Response;
    if (registrationId.equals("naver")) {
      oauth2Response = new NaverResponse(oAuth2User.getAttributes());
    } else if (registrationId.equals("kakao")) {
      oauth2Response = new KakaoResponse(oAuth2User.getAttributes());
    } else {
      return null;
    }

    String oauth2Id = oauth2Response.getProvider() + oauth2Response.getProviderId();
    Member member = memberRepository.findByOauth2Id(oauth2Id);
    if (member == null) { // 회원가입
      Member newMember = new Member(oauth2Id, Role.USER);
      Member savedMember = memberRepository.save(newMember);
      OauthUserDto userDto = new OauthUserDto(savedMember.getMemberSeq(), oauth2Id, Role.USER.getDesc());
      return new CustomOauth2User(userDto);
    } else { // 로그인
      OauthUserDto userDto = new OauthUserDto(member.getMemberSeq(), oauth2Id, member.getRole().getDesc());
      return new CustomOauth2User(userDto);
    }
  }
}
