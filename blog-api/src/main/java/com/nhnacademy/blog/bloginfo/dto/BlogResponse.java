package com.nhnacademy.blog.bloginfo.dto;

import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.category.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;


@Value
@RequiredArgsConstructor
public class BlogResponse {
    Long blogId;
    List<Category> categories;
    List<BlogMemberMapping> blogMemberMappings;
    String blogFid;
    boolean blogMain;
    String blogName;
    String blogMbNickName;
    String blogDescription;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean blogIsPublic;
}
