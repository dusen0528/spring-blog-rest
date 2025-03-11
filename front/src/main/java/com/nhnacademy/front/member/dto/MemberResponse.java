package com.nhnacademy.front.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
@JacksonXmlRootElement(localName = "member")
public class MemberResponse {


    //회원_번호
    @JsonProperty("no")
    private final Long mbNo;

    //회원_이메일
    @JsonProperty("email")
    private final String mbEmail;

    //회원_이름
    @JsonProperty("name")
    private final String mbName;

    //모바일 연락처
    @JsonProperty("mobile")
    private final String mbMobile;

    //가입일자
    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    //수정일자
    @JsonIgnore
    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    /// 탈퇴일자
    @JsonProperty("withdrawalAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime withdrawalAt;

}