package com.nhnacademy.blog.bloginfo.dto;


import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class BlogRequest {
    String blogFid;
    boolean blogMain;
    String blogName;
    String blogMbNickName;
    String blogDescription;
}
