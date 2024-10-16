package com.freshman.freshmanbackend.domain.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저정보 수정 DTO
 */
@Getter
@Setter
public class MemberUpdateRequest {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String address;
    @NotNull
    private String addressDetail;
    @NotNull
    private String phone;
}
