package com.freshman.freshmanbackend.global.common.response;

/**
 * 에러 공통 응답 형식
 *
 * @author 송병선
 */
public record ErrorResponse(int status, String message) {
}
