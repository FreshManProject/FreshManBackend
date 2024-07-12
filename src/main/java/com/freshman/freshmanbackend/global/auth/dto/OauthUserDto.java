package com.freshman.freshmanbackend.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * OauthUserDto
 */
@AllArgsConstructor
@Getter
@Setter
public class OauthUserDto {
    private String oauth2Id;
    private String role;
}
