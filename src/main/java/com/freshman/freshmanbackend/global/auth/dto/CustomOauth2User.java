package com.freshman.freshmanbackend.global.auth.dto;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 로그인 사용자 정보
 */
@RequiredArgsConstructor
public class CustomOauth2User implements OAuth2User {
    private final OauthUserDto userDto;
    private final MemberRepository memberRepository;

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

    @Override
    public String getName() {
        return userDto.getOauth2Id();
    }

    public Long getMemberSeq(){
        Long memberSeq = userDto.getMemberSeq();
        if (memberSeq == null){
            Member member = memberRepository.findByOauth2Id(userDto.getOauth2Id());
            userDto.setMemberSeq(member.getMemberSeq());
            return member.getMemberSeq();
        }
        return memberSeq;
    }
}
