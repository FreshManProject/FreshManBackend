package com.freshman.freshmanbackend.domain.member.controller;

import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberResponse;
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
    public ResponseEntity<?> save(@RequestBody @Validated MemberUpdateRequest memberUpdateRequest){
        memberService.save(memberUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @GetMapping
    public ResponseEntity<?> get(){
        MemberResponse memberInfo = memberService.get();
        return ResponseEntity.ok(new DataResponse(memberInfo));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated MemberUpdateRequest memberUpdateRequest){
        memberService.update(memberUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @DeleteMapping
    public ResponseEntity<?> delete(){
        memberService.delete();
        return ResponseEntity.ok(new SuccessResponse());
    }
}
