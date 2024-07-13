package com.freshman.freshmanbackend.global.auth.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

/**
 * 현재 로그인한 유저의 정보를 처리하는 유틸 클래스
 */
@UtilityClass
public class AuthMemberUtils {
    public String getCurrentUserOauth2Id(){
        Principal principal =  (Principal) getAuthentication().getPrincipal();
        return principal.getName();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
