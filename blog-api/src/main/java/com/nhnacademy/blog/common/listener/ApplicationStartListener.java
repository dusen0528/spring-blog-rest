package com.nhnacademy.blog.common.listener;

import com.nhnacademy.blog.common.security.PasswordEncoder;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * local환경에서의 테스트 데이터 생성
 * Spring Application 시작시 블로그 회원가입 블로그 생성
 * - ApplicationReadyEvent 를 구독 합니다.
 * - erd를 기반으로 기본데이터를 생성합니다.
 * - 데이터는 h2에 저장됩니다.
 */

@Profile("local")
@Component
@RequiredArgsConstructor
public class ApplicationStartListener implements ApplicationListener<ApplicationReadyEvent> {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for(int i=1; i<=10; i++){
            String mbEmail = "marco%s@nhnacademy.com".formatted(i);
            String mbName = "마르코%s".formatted(i);
            String mbPassword = passwordEncoder.encode(mbName);
            String s = i < 10 ? "0%s".formatted(i) : String.valueOf(i);
            String mbMobile = "0112222000%s".formatted(s);

            //member 등록
            Member member = Member.ofNewMember(
                    mbEmail,
                    mbName,
                    mbPassword,
                    mbMobile
            );

            memberRepository.save(member);
        }
    }
}
