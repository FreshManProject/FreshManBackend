package com.freshman.freshmanbackend.domain.member.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.member.request.MemberAddressUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberResponse;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 멤버 서비스
 */
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 멤버 삭제
     */
    @Transactional
    public void delete() {
        String oauth2Id = AuthMemberUtils.getUserOauth2Id();
        memberRepository.deleteByOauth2Id(oauth2Id);
    }

    /**
     * 멤버 조회
     */
    @Transactional(readOnly = true)
    public MemberResponse get() {
        String oauth2Id = AuthMemberUtils.getUserOauth2Id();
        Member member = memberRepository.findByOauth2Id(oauth2Id);
        return MemberResponse.toResponse(member);
    }

    /**
     * 멤버 저장
     */
    @Transactional
    public void save(MemberUpdateRequest request) {
        String oauth2Id = AuthMemberUtils.getUserOauth2Id();
        Member member = memberRepository.findByOauth2Id(oauth2Id);
        member.registerMember(request);
    }

    /**
     * 멤버 정보 전체 수정
     */
    @Transactional
    public void update(MemberUpdateRequest request) {
        String oauth2Id = AuthMemberUtils.getUserOauth2Id();
        Member member = memberRepository.findByOauth2Id(oauth2Id);
        member.updateMember(request);
    }

    /**
     * 회원 개인 정보만 업데이트
     *
     * @param request 회원 개인정보
     */
    @Transactional
    public void updateInfo(MemberInfoUpdateRequest request) {
        String userOauth2Id = AuthMemberUtils.getUserOauth2Id();
        Member member = memberRepository.findByOauth2Id(userOauth2Id);
        member.updateInfo(request);
    }

    /**
     * 회원 주소 업데이트
     *
     * @param request 회원 주소
     */
    @Transactional
    public void updateAddress(MemberAddressUpdateRequest request) {
        String userOauth2Id = AuthMemberUtils.getUserOauth2Id();
        Member member = memberRepository.findByOauth2Id(userOauth2Id);
        member.updateAddress(request);
    }
}
