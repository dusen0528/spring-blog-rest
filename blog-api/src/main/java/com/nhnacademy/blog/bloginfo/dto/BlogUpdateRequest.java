package com.nhnacademy.blog.bloginfo.dto;

import lombok.Value;

@Value
public class BlogUpdateRequest {

    String blogFid;
    String blogName;
    String blogMbNickname;
    String blogDescription;
    Boolean blogIsPublic;



}
