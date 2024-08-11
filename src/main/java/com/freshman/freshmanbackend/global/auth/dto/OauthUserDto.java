package com.freshman.freshmanbackend.global.auth.dto;

import com.freshman.freshmanbackend.domain.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * OauthUserDto
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OauthUserDto {
  private Long memberSeq;
  private String oauth2Id;
  private String role;
  private String name;
  private String email;
  private String address;
  private String phoneNumber;

  public static OauthUserDto fromMember(Member member, String role) {
    return OauthUserDto.builder()
                       .memberSeq(member.getMemberSeq())
                       .oauth2Id(member.getOauth2Id())
                       .role(role)
                       .name(member.getName())
                       .name(member.getName())
                       .email(member.getEmail())
                       .address(member.getAddress())
                       .phoneNumber(member.getPhoneNumber())
                       .build();
  }
}
