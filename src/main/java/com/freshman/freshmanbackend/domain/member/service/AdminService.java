package com.freshman.freshmanbackend.domain.member.service;

import com.freshman.freshmanbackend.domain.member.domain.Admin;
import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.domain.enums.Role;
import com.freshman.freshmanbackend.domain.member.repository.AdminRepository;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.member.request.AdminLoginRequest;
import com.freshman.freshmanbackend.domain.member.response.AdminLoginResponse;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public AdminLoginResponse login(AdminLoginRequest request){
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ValidationException("member.not_found"));
        Admin admin = adminRepository.findById(member.getMemberSeq()).orElseThrow(() -> new ValidationException("admin.not_admin"));
        if (!admin.getPassword().equals(request.getPassword())) throw new ValidationException("admin.wrong_password");
        String accessToken = jwtUtil.createJwt("access_token", member.getOauth2Id(), Role.ADMIN.getDesc(), 600000L);
        String refreshToken = jwtUtil.createJwt("refresh_token", member.getOauth2Id(), Role.ADMIN.getDesc(), 864000000L);
        return new AdminLoginResponse(accessToken, refreshToken);
    }
}
