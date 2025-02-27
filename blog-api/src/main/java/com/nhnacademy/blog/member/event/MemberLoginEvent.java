package com.nhnacademy.blog.member.event;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * MemberLoginEvent는 회원이 로그인 성공 시 발생하는 이벤트입니다.
 */


/**
 * Spring에서 custom event를 생성하기 위해서는 ApplicationEvent를 상속해야 합니다.
 */
public class MemberLoginEvent extends ApplicationEvent {
    //이메일
    private final String email;

    /**
     * 생성자: MemberLoginEvent를 생성할 때 이벤트 소스와 이메일을 전달합니다.
     *
     * @param source 이벤트를 발생시킨 객체 (보통은 이벤트가 발생한 클래스 자체)
     * @param email 로그인한 사용자의 이메일 주소
     */
    public MemberLoginEvent(Object source, String email) {
        super(source);
        this.email = email;

        //source, email validation
        if( Objects.isNull(source) ||  Objects.isNull(email) || email.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    //email gatter method를 작성 합니다.
    public String getEmail() {
        return email;
    }
}
