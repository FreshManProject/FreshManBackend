package com.freshman.freshmanbackend.domain.member.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.freshman.freshmanbackend.domain.member.domain.Admin;
import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.domain.enums.Role;
import com.freshman.freshmanbackend.domain.member.repository.AdminRepository;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.member.request.AdminLoginRequest;
import com.freshman.freshmanbackend.domain.member.response.AdminLoginResponse;
import com.freshman.freshmanbackend.global.auth.util.JwtUtil;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 어드민 서비스 테스트
 */
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("어드민 로그인에 성공하여 액세스토큰과 리프레시 토큰을 반환한다.")
    public void testLoginSuccess() {
        //given
        final AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
        final Member member = new Member("test", Role.ADMIN);
        ReflectionTestUtils.setField(member, "memberSeq", 1L);
        final Admin admin = new Admin(1L, "admin");

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(adminRepository.findById(anyLong())).thenReturn(Optional.of(admin));
        when(jwtUtil.createJwt(eq("access_token"), anyString(), anyString(), anyLong())).thenReturn("access");
        when(jwtUtil.createJwt(eq("refresh_token"), anyString(), anyString(), anyLong())).thenReturn("refresh");

        //when
        final AdminLoginResponse response = adminService.login(request);

        //then
        assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.getAccessToken()).isEqualTo("access");
            softly.assertThat(response.getRefreshToken()).isEqualTo("refresh");
        });
    }

    @Test
    @DisplayName("어드민 로그인 실패 케이스 - 멤버 존재 안함")
    public void testLoginFailedMemberNotFound() {
        //given
        AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        //when & then
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> adminService.login(request));
        assertEquals("validation.member.not_found", exception.getMessage());
    }

    @Test
    @DisplayName("어드민 로그인 실패 케이스 - 어드민 아님")
    public void testLoginFailedNotAdmin() {
        //given
        AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
        Member member = new Member("test", Role.ADMIN);
        ReflectionTestUtils.setField(member, "memberSeq", 1L);
        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(member));
        when(adminRepository.findById(member.getMemberSeq())).thenReturn(Optional.empty());

        //when & then
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> adminService.login(request));
        assertEquals("validation.admin.not_admin", exception.getMessage());
    }

    @Test
    @DisplayName("어드민 로그인 실패 케이스 - 비밀번호가 다름")
    public void testLoginFailedWrongPassword() {
        //given
        AdminLoginRequest request = new AdminLoginRequest("admin", "not_admin");
        Member member = new Member("test", Role.ADMIN);
        ReflectionTestUtils.setField(member, "memberSeq", 1L);
        Admin admin = new Admin(1L, "admin");
        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(member));
        when(adminRepository.findById(member.getMemberSeq())).thenReturn(Optional.of(admin));

        //when & then
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> adminService.login(request));
        assertEquals("validation.admin.wrong_password", exception.getMessage());
    }
}
