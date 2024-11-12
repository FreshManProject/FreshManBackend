package com.freshman.freshmanbackend.domain.member.service;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.domain.enums.Role;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    private final String OAUTH2_ID = "testOauth2Id";
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    private MockedStatic<AuthMemberUtils> mockStatic;

    @BeforeEach
    public void setUp() {
        mockStatic = mockStatic(AuthMemberUtils.class);
    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
    }

    @Test
    @DisplayName("멤버를 삭제한다.")
    public void testDelete() {
        //given
        when(AuthMemberUtils.getUserOauth2Id()).thenReturn(OAUTH2_ID);
        //when
        memberService.delete();
        //then
        verify(memberRepository, times(1)).deleteByOauth2Id(OAUTH2_ID);
    }

    @Test
    @DisplayName("멤버를 저장한다")
    public void testSave() {
        //given
        MemberUpdateRequest request = new MemberUpdateRequest("name", "email", "address", "addressDetail", "phone");
        Member member = new Member(OAUTH2_ID, Role.USER);
        when(AuthMemberUtils.getUserOauth2Id()).thenReturn(OAUTH2_ID);
        when(memberRepository.findByOauth2Id(OAUTH2_ID)).thenReturn(member);
        //when
        memberService.save(request);

        //then
    }

    @Test
    @DisplayName("멤버를 조회한다.")
    public void testGet() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("멤버 정보 전체를 수정한다")
    public void testUpdate() {

    }

    @Test
    @DisplayName("회원의 개인정보만 업데이트한다")
    public void testUpdateInfo() {

    }

    @Test
    @DisplayName("회원의 주소를 업데이트한다")
    public void tesetUpdateAddress() {

    }
}
