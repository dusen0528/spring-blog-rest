package com.nhnacademy.blog.common.security;

public interface PasswordEncoder {
    /**
     * 비밀번호 암호화
     * @param rawPassword 원본 비밀번호
     * @return
     */
    String encode(String rawPassword);

    /**
     * 비밀번호 일치여부 체크
     * @param rawPassword 원본 비밀번호
     * @param encodedPassword 암호화된 비밀번호
     * @return 일치하면 true, 일치하지 않다면 false
     */
    boolean matches(String rawPassword, String encodedPassword);
}
