package com.freshman.freshmanbackend.domain.member.controller;

import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberInfoResponse;
import com.freshman.freshmanbackend.domain.member.service.MemberService;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.response.DataResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 멤버 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> saveMember(@RequestBody @Validated MemberInfoUpdateRequest memberInfoUpdateRequest){
        String oauth2Id = AuthMemberUtils.getCurrentUserOauth2Id();
        memberService.saveMemberInfo(memberInfoUpdateRequest, oauth2Id);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @GetMapping
    public ResponseEntity<?> getMember(){
        String oauth2Id = AuthMemberUtils.getCurrentUserOauth2Id();
        MemberInfoResponse memberInfo = memberService.getMemberInfo(oauth2Id);
        return ResponseEntity.ok(new DataResponse(memberInfo));
    }

    @PutMapping()
    public ResponseEntity<?> updateMember(@RequestBody @Validated MemberInfoUpdateRequest memberInfoUpdateRequest){
        String oauth2Id = AuthMemberUtils.getCurrentUserOauth2Id();
        memberService.updateMemberInfo(oauth2Id, memberInfoUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteMember(){
        String oauth2Id = AuthMemberUtils.getCurrentUserOauth2Id();
        memberService.deleteMemberInfo(oauth2Id);
        return ResponseEntity.ok(new SuccessResponse());
    }
}
