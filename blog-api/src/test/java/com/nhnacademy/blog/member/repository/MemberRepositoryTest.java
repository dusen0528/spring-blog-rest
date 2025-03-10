package com.nhnacademy.blog.member.repository;

import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    Member member = null;

    @BeforeEach
    void setUp(){
        member = Member.ofNewMember(
                "test@naver.com",
                "testName",
                "test123",
                "01012341234"
        );

        memberRepository.save(member);
    }

    @AfterEach
    void tearDown(){memberRepository.delete(member);}

    @Test
    @DisplayName("이메일 - 멤버찾기")
    void findByMbEmail() {
        Optional<Member> testMember = memberRepository.findByMbEmail(member.getMbEmail());

        assertNotNull(testMember);

        assertAll(
                ()->assertEquals("test@naver.com", testMember.get().getMbEmail()),
                ()->assertEquals("testName", testMember.get().getMbName()),
                ()->assertEquals("test123", testMember.get().getMbPassword()),
                ()->assertEquals("01012341234", testMember.get().getMbMobile())
        );

    }

    @Test
    @DisplayName("존재하지 않는 이메일로 멤버 찾기")
    void findByMbEmail_NotFound() {
        Optional<Member> testMember = memberRepository.findByMbEmail("nonexistent@test.com");
        assertTrue(testMember.isEmpty(), "존재하지 않는 이메일은 빈 결과를 반환해야 합니다.");
    }

    @Test
    @DisplayName("폰번호 - 멤버찾기")
    void findByMbMobile() {
        Optional<Member> testMember = memberRepository.findByMbMobile(member.getMbMobile());

        assertNotNull(testMember);

        assertAll(
                ()->assertEquals("test@naver.com", testMember.get().getMbEmail()),
                ()->assertEquals("testName", testMember.get().getMbName()),
                ()->assertEquals("test123", testMember.get().getMbPassword()),
                ()->assertEquals("01012341234", testMember.get().getMbMobile())
        );
    }

    @Test
    @DisplayName("존재하지 않는 폰번호로 멤버 찾기")
    void findByMbMobile_NotFound() {
        Optional<Member> testMember = memberRepository.findByMbMobile("01033334444");
        assertTrue(testMember.isEmpty(), "존재하지 않는 전화번호는 빈 결과를 반환해야 합니다.");
    }

    @Test
    @DisplayName("이메일 존재 여부 확인")
    void existsByMbEmail() {
        boolean exists = memberRepository.existsByMbEmail(member.getMbEmail());
        assertTrue(exists, "이메일이 존재해야 합니다.");
    }

    @Test
    @DisplayName("휴대폰 번호 존재 여부 확인")
    void existsByMbMobile() {
        boolean exists = memberRepository.existsByMbMobile(member.getMbMobile());
        assertTrue(exists, "휴대폰 번호가 존재해야 합니다.");
    }

    @Test
    @DisplayName("탈퇴한 멤버 조회 - 생성일 오름차순")
    void findByWithdrawalAtIsNotNullOrderByCreatedAtAsc() {
        // 탈퇴 날짜 설정
        member.withdraw();
        memberRepository.save(member);

        List<Member> withdrawnMembers = memberRepository.findByWithdrawalAtIsNotNullOrderByCreatedAtAsc();
        assertFalse(withdrawnMembers.isEmpty(), "탈퇴한 멤버가 조회되어야 합니다.");
        assertEquals(member.getMbEmail(), withdrawnMembers.get(0).getMbEmail(), "탈퇴한 멤버가 올바르게 조회되어야 합니다.");
    }

    @Test
    @DisplayName("탈퇴하지 않은 멤버 조회 - 생성일 내림차순")
    void findByWithdrawalAtIsNullOrderByCreatedAtDesc() {
        List<Member> activeMembers = memberRepository.findByWithdrawalAtIsNullOrderByCreatedAtDesc();
        assertFalse(activeMembers.isEmpty(), "탈퇴하지 않은 멤버가 조회되어야 합니다.");
        assertEquals(member.getMbEmail(), activeMembers.get(0).getMbEmail(), "활성 상태의 멤버가 올바르게 조회되어야 합니다.");
    }

    @Test
    @DisplayName("탈퇴한 멤버 수 카운트")
    void countByWithdrawalAtIsNotNull() {
        // 탈퇴 날짜 설정
        member.withdraw();
        memberRepository.save(member);

        long count = memberRepository.countByWithdrawalAtIsNotNull();
        assertEquals(1, count, "탈퇴한 멤버 수가 정확해야 합니다.");
    }

    @Test
    @DisplayName("탈퇴하지 않은 멤버 수 카운트")
    void countByWithdrawalAtIsNull() {
        long count = memberRepository.countByWithdrawalAtIsNull();
        assertEquals(1, count, "탈퇴하지 않은 멤버 수가 정확해야 합니다.");
    }

    @Test
    @DisplayName("모든 멤버 조회 - 생성일 오름차순")
    void findAllByOrderByCreatedAtAsc() {
        List<MemberResponse> members = memberRepository.findAllByOrderByCreatedAtAsc();
        assertFalse(members.isEmpty(), "멤버 목록이 비어있으면 안 됩니다.");
        assertEquals(member.getMbEmail(), members.get(0).getMbEmail(), "멤버 목록이 올바르게 조회되어야 합니다.");
    }
}