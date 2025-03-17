package com.nhnacademy.blog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    private String mbEmail;
    private String mbPassword;

    public MemberLoginRequest(String mbEmail, String mbPassword){
        this.mbEmail =mbEmail;
        this.mbPassword = mbPassword;

    }
}
