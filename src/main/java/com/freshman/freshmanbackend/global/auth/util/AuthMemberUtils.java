package com.freshman.freshmanbackend.global.auth.util;

import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;
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
        CustomOauth2User oauth2User =  (CustomOauth2User) getAuthentication().getPrincipal();
        return oauth2User.getName();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
