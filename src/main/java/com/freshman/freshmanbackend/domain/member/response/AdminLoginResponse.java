package com.freshman.freshmanbackend.domain.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminLoginResponse {
    private String accessToken;
    private String refreshToken;
}
