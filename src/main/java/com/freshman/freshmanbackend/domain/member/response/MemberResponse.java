package com.freshman.freshmanbackend.domain.member.response;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 멤버 정보 응답 객체
 */
@Getter
@Setter
@AllArgsConstructor
public class MemberResponse {
    private String name;
    private String email;
    private String address;
    private String phone;
    private Boolean initialized;

    public static MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getName(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber(),
                member.getInit());
    }
}
