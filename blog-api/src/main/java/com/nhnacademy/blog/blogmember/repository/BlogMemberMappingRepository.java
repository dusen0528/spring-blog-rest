package com.nhnacademy.blog.blogmember.repository;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.member.domain.Member;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogMemberMappingRepository extends JpaRepository<BlogMemberMapping, Long> {
    //method name에 이미 member는 포함되어 있음으로 blog만 추가 해주면 해당 method가 실행 될 때 , member, blog를 함께 join 합니다.
    //이럴때 사용하는것이 @EntityGraph 입니다.

    /*
    멤버 번호로 매핑된 블로그 찾기
     */
    @EntityGraph(attributePaths = {"blog"})
    List<BlogMemberMapping> findBlogMemberMappingsByMember_MbNo(Long mbNo);

    /*
    블로그 ID로 블로그 멤버 매핑 조회
     */
    @EntityGraph(attributePaths = {"member"})
    List<BlogMemberMapping> findBlogMemberMappingsByBlog_BlogId(Long blogId);

    /*
    멤버와 블로그 id로 블로그 멤버 매핑 조회
     */
    @EntityGraph(attributePaths = {"member", "blog"})
    Optional<BlogMemberMapping> findBlogMemberMappingByMember_MbNoAndBlog_BlogId(Long memberId, Long blogId);



}
