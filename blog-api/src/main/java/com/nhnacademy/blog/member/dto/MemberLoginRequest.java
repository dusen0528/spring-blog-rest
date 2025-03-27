package com.nhnacademy.blog.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    private String mbEmail;
    private String mbPassword;

    @JsonCreator
    public MemberLoginRequest(@JsonProperty("email") String mbEmail, @JsonProperty("password") String mbPassword){
        this.mbEmail =mbEmail;
        this.mbPassword = mbPassword;

    }



}
