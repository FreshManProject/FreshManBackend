package com.freshman.freshmanbackend.domain.member.controller;

import com.freshman.freshmanbackend.domain.member.controller.validator.MemberValidator;
import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberInfoResponse;
import com.freshman.freshmanbackend.domain.member.service.MemberService;
import com.freshman.freshmanbackend.global.common.response.DataResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 멤버 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/user")
    public ResponseEntity<?> saveMemberInfo(@RequestBody @Validated MemberInfoUpdateRequest memberInfoUpdateRequest, Principal principal){
        memberService.saveMemberInfo(memberInfoUpdateRequest, principal.getName());
        return ResponseEntity.ok(new SuccessResponse());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getMemberInfo(Principal principal){
        MemberInfoResponse memberInfo = memberService.getMemberInfo(principal.getName());
        return ResponseEntity.ok(new DataResponse(memberInfo));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateMemberInfo(@RequestBody @Validated MemberInfoUpdateRequest memberInfoUpdateRequest, Principal principal){
        memberService.updateMemberInfo(principal.getName(), memberInfoUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteMemberInfo(Principal principal){
        memberService.deleteMemberInfo(principal.getName());
        return ResponseEntity.ok(new SuccessResponse());
    }
}
