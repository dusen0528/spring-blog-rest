package com.nhnacademy.blog.member.repository;

import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    /**
     * 이메일로 회원조회
     * select * from members where mb_email='marco@nhnacademy.com'
     */
    Optional<Member> findByMbEmail(String mbEmail);

    /**
     * 전화번호로 회원조회
     * select * from members where mb_mobile='01011112222'
     */
    Optional<Member> findByMbMobile(String mbMobile);

    /**
     *  이메일에 해당되는 회원이 존재하는지 체크
     * select 1 from members where mb_email='marco@nhnacademy.com'
     */
    boolean existsByMbEmail(String mbEmail);

    /**
     *  전화번호에 해당하는 회원이 존재하는지 체크
     */
    boolean existsByMbMobile(String mbMobile);

    /**
     * 이용중인 회원 조회, 가입일자 기준으로 오름차순 정렬
     * select * from members where withdrawal_at is not null order by created_at asc
     */
    List<Member> findByWithdrawalAtIsNotNullOrderByCreatedAtAsc();

    /**
     * 탈퇴회원 조회 가입일자 기준 내림차순 정렬
     * select * from members where withdrawal_at is null order by created_at desc
     */
    List<Member> findByWithdrawalAtIsNullOrderByCreatedAtDesc();

    /**
     * 이용중인 회원 수
     * select count(*) from members where withdrawal_at is not null
     */
    long countByWithdrawalAtIsNotNull();

    /**
     * 탈퇴한 회원 수
     * select count(*) from members where withdrawal_at is null
     */
    long countByWithdrawalAtIsNull();

}
