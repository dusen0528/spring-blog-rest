package com.nhnacademy.blog.auth.service;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;

public interface AuthService {

    MemberResponse authRegister(MemberRegisterRequest memberRegisterRequest);
}
