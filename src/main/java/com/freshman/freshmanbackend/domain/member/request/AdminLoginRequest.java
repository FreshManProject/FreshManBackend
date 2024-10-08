package com.freshman.freshmanbackend.domain.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLoginRequest {
    private String email;
    private String password;
}
