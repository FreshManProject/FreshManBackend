package com.freshman.freshmanbackend.domain.member.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.member.request.MemberAddressUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberResponse;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 멤버 서비스
 */
@RequiredArgsConstructor
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  @Transactional
  public void delete() {
    String oauth2Id = AuthMemberUtils.getUserOauth2Id();
    memberRepository.deleteByOauth2Id(oauth2Id);
  }

  @Transactional(readOnly = true)
  public MemberResponse get() {
    String oauth2Id = AuthMemberUtils.getUserOauth2Id();
    Member member = memberRepository.findByOauth2Id(oauth2Id);
    return MemberResponse.toResponse(member);
  }

  @Transactional
  public void save(MemberUpdateRequest request) {
    String oauth2Id = AuthMemberUtils.getUserOauth2Id();
    Member member = memberRepository.findByOauth2Id(oauth2Id);
    member.registerMember(request);
  }

  @Transactional
  public void update(MemberUpdateRequest request) {
    String oauth2Id = AuthMemberUtils.getUserOauth2Id();
    Member member = memberRepository.findByOauth2Id(oauth2Id);
    member.updateMember(request);
  }

  /**
   * 회원 개인 정보 업데이트
   * @param request 회원 개인정보
   */
  @Transactional
  public void updateInfo(MemberInfoUpdateRequest request){
    String userOauth2Id = AuthMemberUtils.getUserOauth2Id();
    Member member = memberRepository.findByOauth2Id(userOauth2Id);
    member.updateInfo(request);
  }

  /**
   * 회원 주소 업데이트
   * @param request 회원 주소
   */
  @Transactional
  public void updateAddress(MemberAddressUpdateRequest request){
    String userOauth2Id = AuthMemberUtils.getUserOauth2Id();
    Member member = memberRepository.findByOauth2Id(userOauth2Id);
    member.updateAddress(request);
  }
}
