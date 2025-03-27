/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2025. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.blog.controller;

import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.dto.MemberUpdateRequest;
import com.nhnacademy.blog.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{member-no}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable(value = "member-no")Long memberNo) {
        MemberResponse memberResponse = memberService.getMember(memberNo);
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PageableDefault(page = 1,size = 5) Pageable pageable) {
        List<MemberResponse> memberResponseList = memberService.getAllMembers(pageable);
        return ResponseEntity.ok(memberResponseList);
    }

    @PostMapping
    public ResponseEntity<MemberResponse> registerMember(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.registerMember(memberRegisterRequest));
    }

    @DeleteMapping(value = "/{member-no}")
    public ResponseEntity<Void> withdraw(@PathVariable(value = "member-no")Long memberNo){
        memberService.withdrawMember(memberNo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{member-no}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable(value = "member-no")Long memberNo, @Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
        return ResponseEntity
                .ok(memberService.updateMember(memberNo, memberUpdateRequest));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberResponse> getMemberByEmail(@PathVariable String email){
        MemberResponse memberResponse = memberService.getMemberByEmail(email);
        return ResponseEntity.ok(memberResponse);
    }


}
