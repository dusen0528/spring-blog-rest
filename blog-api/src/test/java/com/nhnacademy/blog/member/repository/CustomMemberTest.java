package com.nhnacademy.blog.member.repository;

import com.nhnacademy.blog.member.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
public class CustomMemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("멤버 저장")
    void saveMember() {
        // Given: 테스트 데이터 준비
        Member member = Member.ofNewMember(
                "test@nhnacademy.com",
                "testName",
                "test1234",
                "01012341234"
        );

        // When: 멤버 저장 및 조회
        memberRepository.save(member);

        // Then: 결과 검증
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(member.getMbNo()),
                () -> Assertions.assertEquals("test@nhnacademy.com", member.getMbEmail()),
                () -> Assertions.assertEquals("testName", member.getMbName()),
                () -> Assertions.assertEquals("test1234", member.getMbPassword()),
                () -> Assertions.assertEquals("01012341234", member.getMbMobile()),
                () -> Assertions.assertNotNull(member.getCreatedAt()),
                () -> Assertions.assertNull(member.getUpdatedAt()),
                () -> Assertions.assertNull(member.getWithdrawalAt())
        );
    }

    @Test
    @DisplayName("이메일로 멤버 찾기 테스트")
    void findByEmailTest(){

        // Given: 테스트 데이터 준비
        Member member = Member.ofNewMember(
                "test@nhnacademy.com",
                "testName",
                "test1234",
                "01012341234"
        );

        // When: 멤버 저장 및 조회

        // 데이터 저장 및 영속성 컨텍스트 반영
        testEntityManager.persistAndFlush(member);

        // 영속성 컨텍스트 초기화 (캐시 제거)
        testEntityManager.clear();

        // 실제 Repository 테스트
        Optional<Member> findMember = memberRepository.findByMbEmail("test@nhnacademy.com");

        // Then: 결과 검증
        Assertions.assertTrue(findMember.isPresent());
        Assertions.assertAll(
                () -> Assertions.assertEquals("test@nhnacademy.com", findMember.get().getMbEmail()),
                () -> Assertions.assertEquals("testName", findMember.get().getMbName()),
                () -> Assertions.assertEquals("test1234", findMember.get().getMbPassword()),
                () -> Assertions.assertEquals("01012341234", findMember.get().getMbMobile()),
                () -> Assertions.assertNotNull(findMember.get().getCreatedAt()),
                () -> Assertions.assertNull(findMember.get().getUpdatedAt()),
                () -> Assertions.assertNull(findMember.get().getWithdrawalAt())
        );
    }
}
