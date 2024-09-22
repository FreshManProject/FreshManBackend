package com.freshman.freshmanbackend.domain.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 주소 업데이트 요청
 */
@NoArgsConstructor
@Getter
public class MemberAddressUpdateRequest {
    private String address;
    private String addressDetails;
}
