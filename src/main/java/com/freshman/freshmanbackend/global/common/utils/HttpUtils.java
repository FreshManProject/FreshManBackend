package com.freshman.freshmanbackend.global.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

/**
 * HTTP 관련 로직을 처리하는 유틸 클래스
 */
@UtilityClass
public class HttpUtils {
    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        cookie.setHttpOnly(true);

        return cookie;
    }

    public String getCookieValueByCookieKey(HttpServletRequest request,String cookieName) {
        if (request == null || cookieName == null) return null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
