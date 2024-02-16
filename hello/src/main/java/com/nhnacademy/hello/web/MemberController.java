/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.hello.web;

import com.nhnacademy.auth.domain.Member;
import com.nhnacademy.hello.adapter.MemberAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberAdapter memberAdapter;

    public MemberController(MemberAdapter memberAdapter) {
        this.memberAdapter = memberAdapter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") String id){
        Optional<Member> memberOptional = memberAdapter.getMember(id);
        if(memberOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(memberOptional.get());
    }

}
