package com.freshman.freshmanbackend.domain.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 개인정보 수정 요청
 */
@Getter
@NoArgsConstructor
public class MemberInfoUpdateRequest {
        private String name;
        private String email;
        private String phone;
}
