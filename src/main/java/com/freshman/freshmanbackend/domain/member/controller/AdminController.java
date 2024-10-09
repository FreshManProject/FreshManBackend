package com.freshman.freshmanbackend.domain.member.controller;

import com.freshman.freshmanbackend.domain.member.request.AdminLoginRequest;
import com.freshman.freshmanbackend.domain.member.response.AdminLoginResponse;
import com.freshman.freshmanbackend.domain.member.service.AdminService;
import com.freshman.freshmanbackend.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 어드민 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * 어드민 로그인
     * @param adminLoginRequest 이메일과 비밀번호
     * @return 액세스 토큰, 리프레시 토큰
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest adminLoginRequest){
        AdminLoginResponse tokens = adminService.login(adminLoginRequest);
        return ResponseEntity.ok(new DataResponse(tokens));
    }
}
