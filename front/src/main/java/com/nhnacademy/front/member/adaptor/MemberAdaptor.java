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

package com.nhnacademy.front.member.adaptor;


import com.nhnacademy.front.member.dto.MemberRegisterRequest;
import com.nhnacademy.front.member.dto.MemberResponse;
import com.nhnacademy.front.member.dto.MemberUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@FeignClient(name = "memberAdaptor", url = "${api.blog.url}", path = "/api/blog/members")
public interface MemberAdaptor {
    @GetMapping("/{member-no}")
    ResponseEntity<MemberResponse> getMember(@PathVariable(value = "member-no")Long memberNo);

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@RequestParam(defaultValue = "1") Integer page );

    @PostMapping
    ResponseEntity<MemberResponse> registerMember(@RequestBody MemberRegisterRequest memberRegisterRequest);

    @DeleteMapping(value = "/{member-no}")
    ResponseEntity<Void> withdraw(@PathVariable(value = "member-no")Long memberNo);

    @PutMapping(value = "/{member-no}")
    ResponseEntity<MemberResponse> updateMember(@PathVariable(value = "member-no")Long memberNo, @RequestBody MemberUpdateRequest memberUpdateRequest);

    @DeleteMapping("/{member-no}")
    ResponseEntity<Void> deleteMember(@PathVariable(value = "member-no")Long memberNo);
}
