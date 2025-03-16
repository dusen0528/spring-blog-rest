package com.nhnacademy.blog.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;


@Getter
@ToString
public class MemberRegisterRequest {

    // 회원 이메일

    private final String mbEmail;

    // 회원 이름
    private final String mbName;

    // 회원 비밀번호
    private final String mbPassword;

    // 모바일 연락처
    private final String mbMobile;

    // 블로그 식별자
    private final String blogFid;

    private final Integer topicId;

    private final String categoryName;

    @JsonCreator
    public MemberRegisterRequest(
            @JsonProperty("email") String mbEmail,
            @JsonProperty("name") String mbName,
            @JsonProperty("password") String mbPassword,
            @JsonProperty("mobile") String mbMobile,
            @JsonProperty("blogFid") String blogFid,
            @JsonProperty("topicId") Integer topicId,
            @JsonProperty("categoryName")String categoryName
    ) {
        this.mbEmail = mbEmail;
        this.mbName = mbName;
        this.mbPassword = mbPassword;
        this.mbMobile = mbMobile;
        this.blogFid = blogFid;
        this.topicId = topicId;
        this.categoryName = categoryName;
    }
}
