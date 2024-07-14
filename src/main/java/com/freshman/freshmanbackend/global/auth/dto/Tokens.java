package com.freshman.freshmanbackend.global.auth.dto;

/**
 * 액세스 토큰과 리프레시 토큰을 가지는 레코드
 */
public record Tokens(String accessToken, String refreshToken) {
}
