package com.freshman.freshmanbackend.domain.member.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberResponse;
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

    @Transactional
    public void save(MemberUpdateRequest request, String oauth2Id) {
        Member member = memberRepository.findByOauth2Id(oauth2Id);
        member.registerMember(request);
    }

    @Transactional(readOnly = true)
    public MemberResponse get(String oauth2Id) {
        Member member = memberRepository.findByOauth2Id(oauth2Id);
        return MemberResponse.toResponse(member);
    }

    @Transactional
    public void update(String oauth2Id, MemberUpdateRequest request) {
        Member member = memberRepository.findByOauth2Id(oauth2Id);
        member.updateMember(request);
    }

    @Transactional
    public void delete(String oauth2Id) {
        memberRepository.deleteByOauth2Id(oauth2Id);
    }
}
