package com.nhnacademy.blog.member.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberLoginListener {

    @EventListener(classes = MemberLoginEvent.class)
    public void handleMemberLoginEvent(MemberLoginEvent event) {
        //이메일 발송 대신 로그로 대체
        log.debug("로그인알림 이메일:{} 발송",event.getEmail());
    }
}
