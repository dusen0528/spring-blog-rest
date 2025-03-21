package com.nhnacademy.blog.auth.service.impl;

import com.nhnacademy.blog.auth.service.AuthService;
import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.common.exception.UnauthorizedException;
import com.nhnacademy.blog.common.security.JwtProvider;
import com.nhnacademy.blog.common.security.PasswordEncoder;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberLoginRequest;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.repository.RoleRepository;
import com.nhnacademy.blog.topic.domain.Topic;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public MemberResponse authRegister(MemberRegisterRequest memberRegisterRequest) {
        Member member = Member.ofNewMember(
                memberRegisterRequest.getMbEmail(),
                memberRegisterRequest.getMbName(),
                memberRegisterRequest.getMbPassword(),
                memberRegisterRequest.getMbMobile()
        );
        memberRepository.save(member);


        Blog blog = Blog.ofNewBlog(memberRegisterRequest.getBlogFid(),
                true,
                memberRegisterRequest.getMbName()+"의 블로그",
                memberRegisterRequest.getMbName(),
                memberRegisterRequest.getMbName()+"님의 첫 블로그 환영합니다");

        Optional<Role> roleOptional = roleRepository.findRoleByRoleId("ROLE_MEMBER");
        if(roleOptional.isEmpty()){
            throw new NotFoundException("ROLE_MEMBER 를 찾을 수 없습니다");
        }

        BlogMemberMapping blogMemberMapping = BlogMemberMapping.ofNewBlogMemberMapping(
            member,
            blog,
            roleOptional.get()
        );
        blog.addBlogMemberMapping(blogMemberMapping);
        blogRepository.save(blog);

        Optional<Topic> topicOptional = topicRepository.findTopicByTopicId(memberRegisterRequest.getTopicId());
        if(topicOptional.isEmpty()){
            throw new NotFoundException("TopicID: "+memberRegisterRequest.getTopicId()+" 를 찾을 수 없습니다");
        }

        Category category = Category.ofNewRootCategory(blog, topicOptional.get(), memberRegisterRequest.getCategoryName(), 1);
        categoryRepository.save(category);

        return new MemberResponse(
                member.getMbNo(),
                member.getMbEmail(),
                member.getMbName(),
                member.getMbMobile(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getWithdrawalAt());
    }

    @Override
    public String login(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findByMbEmail(memberLoginRequest.getMbEmail())
                .orElseThrow(()->new NotFoundException("이메일 또는 비밀번호가 일치하지 않습니다"));

        if (!passwordEncoder.matches(memberLoginRequest.getMbPassword(), member.getMbPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.generateToken(member.getMbEmail()); // JWT 생성 후 반환
    }
}
