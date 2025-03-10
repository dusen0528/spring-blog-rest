package com.nhnacademy.blog.bloginfo.service;

import com.nhnacademy.blog.bloginfo.dto.BlogRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;

public interface BlogService {

    BlogResponse saveBlog(BlogRequest blogRequest);
    BlogResponse getBlogByFid(String blogFid);
    BlogResponse updateBlog(BlogUpdateRequest blogUpdateRequest);
    void deleteBlogByFid(String blogFid);
}
