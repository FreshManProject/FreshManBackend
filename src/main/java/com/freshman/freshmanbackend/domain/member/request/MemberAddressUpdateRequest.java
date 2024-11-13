package com.freshman.freshmanbackend.domain.member.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 주소 업데이트 요청
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberAddressUpdateRequest {
    private String address;
    private String addressDetails;
}
