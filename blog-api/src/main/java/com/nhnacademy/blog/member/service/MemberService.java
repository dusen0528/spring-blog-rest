package com.nhnacademy.blog.member.service;
import com.nhnacademy.blog.member.dto.BlogAccountRequest;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.dto.MemberUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    MemberResponse registerMember(MemberRegisterRequest memberRegisterRequest);
    MemberResponse getMember(long mbNo);
    MemberResponse getMemberByEmail(String mbEmail);
    List<MemberResponse> getAllMembers(Pageable pageable);
    void withdrawMember(long mbNo);
    MemberResponse updateMember(Long mbNo, MemberUpdateRequest memberUpdateRequest);
}
