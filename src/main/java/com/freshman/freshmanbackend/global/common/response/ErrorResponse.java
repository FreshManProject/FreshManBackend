package com.freshman.freshmanbackend.global.common.response;

/**
 * 에러 공통 응답 형식
 *
 * 
 */
public record ErrorResponse(int status, String message) {
}
