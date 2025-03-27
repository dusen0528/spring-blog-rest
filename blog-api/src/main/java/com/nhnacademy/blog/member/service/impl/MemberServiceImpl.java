package com.nhnacademy.blog.member.service.impl;

import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.common.exception.ConflictException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.common.security.PasswordEncoder;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.dto.MemberUpdateRequest;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlogRepository blogRepository;
    private final BlogService blogService;

    @Transactional
    @Override
    public MemberResponse registerMember(MemberRegisterRequest memberRegisterRequest) {
        //1.이메일 중복체크
        boolean isExistsEmail = memberRepository.existsByMbEmail(memberRegisterRequest.getMbEmail());
        if(isExistsEmail) {
            throw new ConflictException("Member email [%s] already exists".formatted(memberRegisterRequest.getMbEmail()));
        }

        //2. 모바일_연락처 중복체크
        boolean isExistMobile = memberRepository.existsByMbMobile(memberRegisterRequest.getMbMobile());
        if(isExistMobile) {
            throw new ConflictException("Member mobile [%s] already exists".formatted(memberRegisterRequest.getMbMobile()));
        }

        // 3. 회원 생성 및 저장
        String mbPassword = passwordEncoder.encode(memberRegisterRequest.getMbPassword());
        Member member = Member.ofNewMember(
                memberRegisterRequest.getMbEmail(),
                memberRegisterRequest.getMbName(),
                mbPassword,
                memberRegisterRequest.getMbMobile()
        );
        memberRepository.save(member);

        // 4. 메인 블로그 생성 (BlogService 활용)
        blogService.createMainBlogForMember(
                member,
                memberRegisterRequest.getBlogFid(),
                memberRegisterRequest.getCategoryName(),
                memberRegisterRequest.getTopicId()
        );

        Optional<Member> memberOptional = memberRepository.findById(member.getMbNo());
        if(memberOptional.isPresent()) {
            return toMemberResponse(memberOptional.get());
        }

        throw new NotFoundException("Member not found");
    }


    @Override
    public MemberResponse getMember(long mbNo) {
        Optional<Member> memberOptional = memberRepository.findById(mbNo);
        if(memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return toMemberResponse(member);
        }

        throw new NotFoundException("Member not found");
    }

    @Override
    public List<MemberResponse> getAllMembers(Pageable pageable) {
        List<MemberResponse> memberList = new ArrayList<>();
        memberRepository.findAll(pageable).getContent().forEach(member -> {
            memberList.add(toMemberResponse(member));
        });
        return memberList;
    }

    @Transactional
    @Override
    public void withdrawMember(long mbNo) {
        Member member = memberRepository.findById(mbNo).orElseThrow(() -> new NotFoundException("Member not found"));
        if(Objects.nonNull(member.getWithdrawalAt())){
            throw new ConflictException("이미 탈퇴한 회원 입니다.");
        }
        member.withdraw();
    }

    @Transactional
    @Override
    public MemberResponse updateMember(Long mbNo, MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(mbNo).orElseThrow(() -> new NotFoundException("Member not found"));
        member.update(memberUpdateRequest.getMbEmail(), memberUpdateRequest.getMbName(), memberUpdateRequest.getMbPassword());
        memberRepository.save(member);
        return toMemberResponse(member);
    }

    @Override
    public MemberResponse getMemberByEmail(String mbEmail) {
        Optional<Member> memberOptional = memberRepository.findByMbEmail(mbEmail);

        if(memberOptional.isEmpty()){
            throw new NotFoundException("Member not found");
        }

        Member member = memberOptional.get();
        return toMemberResponse(member);

    }

    private MemberResponse toMemberResponse(Member member) {
        String blogFid = blogRepository.findBlogFidFromMainBlog(member.getMbNo());

        return new MemberResponse(
                member.getMbNo(),
                member.getMbEmail(),
                member.getMbName(),
                member.getMbMobile(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getWithdrawalAt(),
                blogFid
        );
    }
}
