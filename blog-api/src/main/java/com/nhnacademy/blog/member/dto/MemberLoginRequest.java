package com.nhnacademy.blog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    private String mbEmail;
    private String mbPassword;
}
