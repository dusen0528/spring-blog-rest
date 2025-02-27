package com.nhnacademy.blog.member.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class BlogAccountRequest {

    //이메일
    private final String email;

    //회원_이름
    private final String name;

    //회원_비밀번호
    private final String password;

    //모바일 연락처
    private final String mobile;

    //블로그_식별_아이디
    private final String blogFid;

}