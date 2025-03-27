package com.nhnacademy.blog.bloginfo.service;

import com.nhnacademy.blog.bloginfo.dto.BlogRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.member.domain.Member;

public interface BlogService {

    BlogResponse saveBlog(BlogRequest blogRequest);
    BlogResponse getBlogByFid(String blogFid);
    BlogResponse updateBlog(BlogUpdateRequest blogUpdateRequest);
    void deleteBlogByFid(String blogFid);
    BlogResponse createMainBlogForMember(Member member, String blogFid, String categoryName, Integer topicId);


}
