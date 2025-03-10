package com.nhnacademy.blog.bloginfo.service.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.dto.BlogRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.common.exception.BadRequestException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

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
}
