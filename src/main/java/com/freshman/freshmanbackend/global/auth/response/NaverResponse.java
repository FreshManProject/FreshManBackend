package com.freshman.freshmanbackend.global.auth.response;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class NaverResponse implements Oauth2Response{
    private final Map<String, Object> attributes;

    public NaverResponse(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>)attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }
}
