package com.nhnacademy.front.blog.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
@JacksonXmlRootElement(localName = "blog")
public class BlogResponse {

    // 블로그 ID
    @JsonProperty("blogId")
    private final Long blogId;

    // 블로그 FID
    @JsonProperty("blogFid")
    private final String blogFid;

    // 메인 블로그 여부
    @JsonProperty("blogMain")
    private final boolean blogMain;

    // 블로그 이름
    @JsonProperty("blogName")
    private final String blogName;

    // 블로그 닉네임
    @JsonProperty("blogMbNickName")
    private final String blogMbNickName;

    // 블로그 설명
    @JsonProperty("blogDescription")
    private final String blogDescription;

    // 생성일자
    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    // 수정일자
    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    // 블로그 공개 여부
    @JsonProperty("blogIsPublic")
    private final Boolean blogIsPublic;

    @JsonCreator

    public BlogResponse(
            @JsonProperty("blogId") Long blogId,
            @JsonProperty("blogFid") String blogFid,
            @JsonProperty("blogMain") boolean blogMain,
            @JsonProperty("blogName") String blogName,
            @JsonProperty("blogMbNickName") String blogMbNickName,
            @JsonProperty("blogDescription") String blogDescription,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("blogIsPublic") Boolean blogIsPublic) {
        this.blogId = blogId;
        this.blogFid = blogFid;
        this.blogMain = blogMain;
        this.blogName = blogName;
        this.blogMbNickName = blogMbNickName;
        this.blogDescription = blogDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blogIsPublic = blogIsPublic;
    }
}