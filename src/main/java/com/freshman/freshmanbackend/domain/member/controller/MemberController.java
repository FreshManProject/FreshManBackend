package com.freshman.freshmanbackend.domain.member.controller;

import com.freshman.freshmanbackend.domain.member.request.MemberAddressUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.domain.member.response.MemberResponse;
import com.freshman.freshmanbackend.domain.member.service.MemberService;
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

    /**
     * 멤버 정보 등록
     * @param memberUpdateRequest 멤버 정보
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Validated MemberUpdateRequest memberUpdateRequest){
        memberService.save(memberUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 멤버 정보 조회
     * @return 멤버 정보
     */
    @GetMapping
    public ResponseEntity<?> get(){
        MemberResponse memberInfo = memberService.get();
        return ResponseEntity.ok(new DataResponse(memberInfo));
    }

    /**
     * 멤버 정보 전체 수정
     * @param memberUpdateRequest 멤버 정보 수정 요청
     */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated MemberUpdateRequest memberUpdateRequest){
        memberService.update(memberUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 멤버 개인정보 수정
     * @param memberInfoUpdateRequest 멤버 개인정보
     */
    @PutMapping("/personal-info")
    public ResponseEntity<?> updateInfo(@RequestBody MemberInfoUpdateRequest memberInfoUpdateRequest){
        memberService.updateInfo(memberInfoUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 멤버 주소 수정
     * @param memberAddressUpdateRequest 멤버 주소
     */
    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(@RequestBody MemberAddressUpdateRequest memberAddressUpdateRequest){
        memberService.updateAddress(memberAddressUpdateRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 멤버 삭제
     */
    @DeleteMapping
    public ResponseEntity<?> delete(){
        memberService.delete();
        return ResponseEntity.ok(new SuccessResponse());
    }
}
