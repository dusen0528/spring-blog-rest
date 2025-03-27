package com.nhnacademy.blog.bloginfo.service.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.dto.BlogRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.blogmember.repository.BlogMemberMappingRepository;
import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.common.exception.BadRequestException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.repository.RoleRepository;
import com.nhnacademy.blog.topic.domain.Topic;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final RoleRepository roleRepository;
    private final BlogMemberMappingRepository blogMemberMappingRepository;
    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BlogResponse saveBlog(BlogRequest blogRequest) {
        Blog blog = Blog.ofNewBlog(
                blogRequest.getBlogFid(),
                blogRequest.isBlogMain(),
                blogRequest.getBlogName(),
                blogRequest.getBlogMbNickName(),
                blogRequest.getBlogDescription()
        );

        blogRepository.save(blog);

        return new BlogResponse(
            blog.getBlogId(),
            blog.getCategories(),
            blog.getBlogMemberMappings(),
            blog.getBlogFid(),
            blog.isBlogMain(),
            blog.getBlogName(),
            blog.getBlogMbNickname(),
            blog.getBlogDescription(),
            blog.getCreatedAt(),
            blog.getUpdatedAt(),
            blog.getBlogIsPublic()
        );


    }

    @Override
    public BlogResponse getBlogByFid(String blogFid) {
        Optional<Blog> blog = blogRepository.findBlogsByBlogFid(blogFid);

        if(blog.isEmpty()){
            throw new NotFoundException("대표아이디 {%s} 블로그를 찾을 수 없다".formatted(blogFid));
        }

        return new BlogResponse(
                blog.get().getBlogId(),
                blog.get().getCategories(),
                blog.get().getBlogMemberMappings(),
                blog.get().getBlogFid(),
                blog.get().isBlogMain(),
                blog.get().getBlogName(),
                blog.get().getBlogMbNickname(),
                blog.get().getBlogDescription(),
                blog.get().getCreatedAt(),
                blog.get().getUpdatedAt(),
                blog.get().getBlogIsPublic()
        );
    }

    @Override
    public BlogResponse updateBlog(BlogUpdateRequest blogUpdateRequest) {
        Optional<Blog> blog =blogRepository.findBlogsByBlogFid(blogUpdateRequest.getBlogFid());

        if(blog.isEmpty() ){
            throw new NotFoundException("대표아이디 {%s} 블로그를 찾을 수 없다".formatted(blogUpdateRequest.getBlogFid()));
        }

        blog.get().update(
                blogUpdateRequest.getBlogName(),
                blogUpdateRequest.getBlogMbNickname(),
                blogUpdateRequest.getBlogDescription(),
                blogUpdateRequest.getBlogIsPublic()
        );
        blogRepository.save(blog.get());

        Optional<Blog> findBlog = blogRepository.findBlogsByBlogFid(blog.get().getBlogFid());


        return new BlogResponse(
                findBlog.get().getBlogId(),
                findBlog.get().getCategories(),
                findBlog.get().getBlogMemberMappings(),
                findBlog.get().getBlogFid(),
                findBlog.get().isBlogMain(),
                findBlog.get().getBlogName(),
                findBlog.get().getBlogMbNickname(),
                findBlog.get().getBlogDescription(),
                findBlog.get().getCreatedAt(),
                findBlog.get().getUpdatedAt(),
                findBlog.get().getBlogIsPublic()
        );
    }

    @Override
    public void deleteBlogByFid(String blogFid) {
        Blog blog = blogRepository.findBlogsByBlogFid(blogFid)
                .orElseThrow(() -> new NotFoundException("{%s} 대표아이디 블로그는 존재하지 않습니다".formatted(blogFid)));

        if (blog.isBlogMain()) {
            throw new BadRequestException("메인 블로그는 삭제 불가능합니다");
        }

        blogRepository.deleteBlogsByBlogFid(blogFid);
    }

    @Override
    public BlogResponse createMainBlogForMember(Member member, String blogFid, String categoryName, Integer topicId) {
        // 1. 메인 블로그 생성
        Blog blog = Blog.ofNewBlog(
                blogFid,
                true, // 메인 블로그로 설정
                member.getMbName() + "의 블로그",
                member.getMbName(), // 닉네임은 회원 이름으로 설정
                member.getMbName() + "님의 첫 블로그 환영합니다" // 기본 설명
        );
        blogRepository.save(blog);

        // 2. ROLE_OWNER 역할 부여
        Role ownerRole = roleRepository.findRoleByRoleId("ROLE_OWNER")
                .orElseThrow(() -> new NotFoundException("ROLE_OWNER를 찾을 수 없습니다"));

        // 3. 블로그-회원 매핑 생성
        BlogMemberMapping blogMemberMapping = BlogMemberMapping.ofNewBlogMemberMapping(member, blog, ownerRole);
        blogMemberMappingRepository.save(blogMemberMapping);

        // 4. 기본 카테고리 생성
        Topic topic = topicRepository.findTopicByTopicId(topicId)
                .orElseThrow(() -> new NotFoundException("TopicID: " + topicId + " 를 찾을 수 없습니다"));

        Category category = Category.ofNewRootCategory(blog, topic, categoryName, 1);
        categoryRepository.save(category);

        return convertToResponse(blog);
    }
    // Blog 엔티티를 BlogResponse DTO로 변환하는 헬퍼 메소드
    private BlogResponse convertToResponse(Blog blog) {
        return new BlogResponse(
                blog.getBlogId(),
                blog.getCategories(),
                blog.getBlogMemberMappings(),
                blog.getBlogFid(),
                blog.isBlogMain(),
                blog.getBlogName(),
                blog.getBlogMbNickname(),
                blog.getBlogDescription(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getBlogIsPublic()
        );
    }
}

