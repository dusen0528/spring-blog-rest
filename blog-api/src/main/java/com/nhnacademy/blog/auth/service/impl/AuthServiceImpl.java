package com.nhnacademy.blog.auth.service.impl;

import com.nhnacademy.blog.auth.service.AuthService;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.common.exception.ConflictException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.common.exception.UnauthorizedException;
import com.nhnacademy.blog.common.security.JwtProvider;
import com.nhnacademy.blog.common.security.PasswordEncoder;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberLoginRequest;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlogService blogService;
    private final JwtProvider jwtProvider;

    @Override
    public MemberResponse authRegister(MemberRegisterRequest memberRegisterRequest) {
        // 1. 이메일 중복체크
        boolean isExistsEmail = memberRepository.existsByMbEmail(memberRegisterRequest.getMbEmail());
        if(isExistsEmail) {
            throw new ConflictException("Member email [%s] already exists".formatted(memberRegisterRequest.getMbEmail()));
        }

        // 2. 모바일_연락처 중복체크
        boolean isExistMobile = memberRepository.existsByMbMobile(memberRegisterRequest.getMbMobile());
        if(isExistMobile) {
            throw new ConflictException("Member mobile [%s] already exists".formatted(memberRegisterRequest.getMbMobile()));
        }

        // 3. 회원 생성 및 저장
        Member member = Member.ofNewMember(
                memberRegisterRequest.getMbEmail(),
                memberRegisterRequest.getMbName(),
                passwordEncoder.encode(memberRegisterRequest.getMbPassword()),
                memberRegisterRequest.getMbMobile()
        );
        memberRepository.save(member);

        // 4. 메인 블로그 생성 (BlogService 활용)
        BlogResponse blogResponse = blogService.createMainBlogForMember(
                member,
                memberRegisterRequest.getBlogFid(),
                memberRegisterRequest.getCategoryName(),
                memberRegisterRequest.getTopicId()
        );

        return new MemberResponse(
                member.getMbNo(),
                member.getMbEmail(),
                member.getMbName(),
                member.getMbMobile(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getWithdrawalAt(),
                blogResponse.getBlogFid()
        );
    }

    @Override
    public String login(MemberLoginRequest memberLoginRequest) {
        log.debug("{} :::  {}", memberLoginRequest.getMbEmail(), memberLoginRequest.getMbPassword());

        Member member = memberRepository.findByMbEmail(memberLoginRequest.getMbEmail())
                .orElseThrow(() -> new NotFoundException("이메일 또는 비밀번호가 일치하지 않습니다"));

        if (!passwordEncoder.matches(memberLoginRequest.getMbPassword(), member.getMbPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.generateToken(member.getMbEmail());
    }
}
