package com.freshman.freshmanbackend.domain.member.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 가입 시 유저정보 등록 DTO
 */
@Getter
@Setter
public class MemberInfoUpdateRequest {
    private String name;
    private String email;
    private String address;
    private String phone;
}
