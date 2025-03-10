package com.nhnacademy.blog.blogmember.repository;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BlogMemberMappingRepositoryTest {

    @Autowired
    private BlogMemberMappingRepository blogMemberMappingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private RoleRepository roleRepository;


    private Member testMember;
    private Blog testBlog;
    private Role testRole;
    private BlogMemberMapping testMapping;

    @BeforeEach
    void setUp() {
        // 테스트용 회원 생성
        testMember = Member.ofNewMember("test@nhnacademy.com", "testUser", "password123", "01012345678");
        memberRepository.save(testMember);

        // 테스트용 블로그 생성
        testBlog = Blog.ofNewBlog("testblog", true, "testBlog", "testNickName", "testDescription");
        blogRepository.save(testBlog);

        // 테스트용 역할 생성 (Role 클래스의 구조에 맞게 수정 필요)
        testRole = new Role("OWNER", "관리자", "TestDescription");
        roleRepository.save(testRole);

        // 테스트용 매핑 생성
        testMapping = BlogMemberMapping.ofNewBlogMemberMapping(
                testMember,
                testBlog,
                testRole
        );

        blogMemberMappingRepository.save(testMapping);
    }


    @Test
    @DisplayName("멤버 번호 - 매핑 찾기")
    void findBlogMemberMappingsByMember_MbNo() {
        // when
        var mappings = blogMemberMappingRepository.findBlogMemberMappingsByMember_MbNo(testMember.getMbNo());

        // then
        assertFalse(mappings.isEmpty());
        assertEquals(1, mappings.size());
        assertEquals(testBlog.getBlogId(), mappings.get(0).getBlog().getBlogId());
        assertEquals(testBlog.getBlogName(), mappings.get(0).getBlog().getBlogName());
        assertEquals(testMember.getMbNo(), mappings.get(0).getMember().getMbNo());
        assertEquals(testRole.getRoleName(), mappings.get(0).getRole().getRoleName());

    }

    @Test
    @DisplayName("블로그 아이디 - 매핑 찾기")
    void findBlogMemberMappingsByBlog_BlogId() {
        // when
        var mappings = blogMemberMappingRepository.findBlogMemberMappingsByBlog_BlogId(testBlog.getBlogId());

        // then
        assertFalse(mappings.isEmpty());
        assertEquals(1, mappings.size());
        assertEquals(testBlog.getBlogId(), mappings.get(0).getBlog().getBlogId());
        assertEquals(testBlog.getBlogName(), mappings.get(0).getBlog().getBlogName());
        assertEquals(testMember.getMbNo(), mappings.get(0).getMember().getMbNo());
        assertEquals(testRole.getRoleName(), mappings.get(0).getRole().getRoleName());

    }

    @Test
    @DisplayName("블로그 번호 + 멤버 번호 - 매핑 찾기")
    void findBlogMemberMappingByMember_MbNoAndBlog_BlogId() {
        // when
        var mappingOptional = blogMemberMappingRepository.findBlogMemberMappingByMember_MbNoAndBlog_BlogId(
                testMember.getMbNo(), testBlog.getBlogId());

        // then
        assertTrue(mappingOptional.isPresent());
        var mapping = mappingOptional.get();
        assertEquals(testMember.getMbNo(), mapping.getMember().getMbNo());
        assertEquals(testBlog.getBlogId(), mapping.getBlog().getBlogId());
        assertEquals(testRole.getRoleName(), mapping.getRole().getRoleName());

        // 존재하지 않는 매핑에 대한 테스트
        var nonExistentMapping = blogMemberMappingRepository.findBlogMemberMappingByMember_MbNoAndBlog_BlogId(
                999L, testBlog.getBlogId());
        assertTrue(nonExistentMapping.isEmpty());

    }
}