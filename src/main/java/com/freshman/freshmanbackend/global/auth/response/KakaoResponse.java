package com.freshman.freshmanbackend.global.auth.response;

import java.util.Map;

/**
 *
 */
public class KakaoResponse implements Oauth2Response{
    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }
}
