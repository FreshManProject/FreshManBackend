package com.freshman.freshmanbackend.domain.member.domain;

import com.freshman.freshmanbackend.domain.member.domain.enums.Role;
import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "MB_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(name = "MB_NM")
    private String name;

    @Column(name = "MB_EMAIL")
    private String email;

    @Column(name = "MB_ADDR")
    private String address;

    @Column(name = "MB_PH_NBR")
    private String phoneNumber;

    @Column(name = "MB_OAUTH_ID", nullable = false)
    private String oauth2Id;

    @Convert(converter = Role.RoleConverter.class)
    @Column(name = "MB_ROLE", nullable = false)
    private Role role;

    @Column(name = "MB_INIT", nullable = false)
    private Boolean init;

    public Member(String oauth2Id, Role role) {
        this.oauth2Id = oauth2Id;
        this.role = role;
        this.init = false;
    }

    //Oauth로 회원 가입 후 멤버 정보 등록
    public Member registerMember(MemberInfoUpdateRequest registerRequest) {
        this.name = registerRequest.getName();
        this.email = registerRequest.getEmail();
        this.address = registerRequest.getAddress();
        this.phoneNumber = registerRequest.getPhone();
        this.init = true;
        return this;
    }

    //멤버 정보 수정
    public Member updateMember(MemberInfoUpdateRequest updateRequest){
        this.name = updateRequest.getName();
        this.email = updateRequest.getEmail();
        this.address = updateRequest.getAddress();
        this.phoneNumber = updateRequest.getPhone();
        return this;
    }
}
