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
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private final String mbEmail;

    // 회원 이름
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private final String mbName;

    // 회원 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    private final String mbPassword;

    // 모바일 연락처
    @NotBlank(message = "모바일 연락처는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^01[0-9]\\d{3,4}\\d{4}$",
            message = "모바일 연락처는 01XXXXXYYYY 형식으로 입력해주세요."
    )
    private final String mbMobile;

    // 블로그 식별자
    @NotBlank(message = "블로그 식별자는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^[a-z][a-z0-9]{2,9}$",
            message = "블로그 아이디는 첫 문자가 영문 소문자여야 하며, 두 번째 문자부터 숫자를 포함할 수 있고, 길이는 3자 이상 10자 이하로 제한됩니다."
    )
    private final String blogFid;

    @NotNull
    private final Integer topicId;


    @NotBlank(message = "기본 카테고리는 필수 입력 항목입니다.")
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
