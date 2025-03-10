package com.nhnacademy.blog.bloginfo.repository;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class BlogRepositoryTest {

    @Autowired
    BlogRepository blogRepository;


    Blog blog = null;


    @BeforeEach
    void setUp(){
        blog = Blog.ofNewBlog(
                "testFid",
                true,
                "testBlogName",
                "testNickName",
                "testDescription"
        );

        blogRepository.save(blog);
    }

    @AfterEach
    void tearDown(){
        blogRepository.delete(blog);
    }

    @Test
    @DisplayName("블로그 아이디 - 블로그 찾기 ")
    void findBlogsByBlogId() {

        Optional<Blog> testBlog = blogRepository.findBlogsByBlogId(blog.getBlogId());

        assertNotNull(testBlog);

        assertAll(
                ()-> assertEquals(true, testBlog.get().isBlogMain()),
                ()-> assertEquals("testFid", testBlog.get().getBlogFid()),
                ()-> assertEquals("testBlogName",testBlog.get().getBlogName()),
                ()-> assertEquals("testNickName", testBlog.get().getBlogMbNickname()),
                ()-> assertEquals("testDescription", testBlog.get().getBlogDescription())
        );
    }

    @Test
    @DisplayName("블로그 대표아이디 - 블로그 찾기")
    void findBlogsByBlogFid() {
        Optional<Blog> testBlog = blogRepository.findBlogsByBlogFid(blog.getBlogFid());

        assertNotNull(testBlog);

        assertAll(
                ()-> assertEquals(true, testBlog.get().isBlogMain()),
                ()-> assertEquals("testFid", testBlog.get().getBlogFid()),
                ()-> assertEquals("testBlogName",testBlog.get().getBlogName()),
                ()-> assertEquals("testNickName", testBlog.get().getBlogMbNickname()),
                ()-> assertEquals("testDescription", testBlog.get().getBlogDescription()),
                ()-> assertNotNull(testBlog.get().getCreatedAt()),
                ()-> assertNull(testBlog.get().getUpdatedAt())

        );
    }

    @Test
    @DisplayName("블로그 아이디로 블로그 있는지 찾기")
    void existsBlogByBlogId() {
        boolean flag = blogRepository.existsBlogByBlogId(blog.getBlogId());

        assertNotNull(flag);
        assertTrue(flag);
    }

    @Test
    @DisplayName("블로그 대표 아이디로 블로그 있는지 찾기")
    void existsBlogByBlogFid() {
        boolean flag = blogRepository.existsBlogByBlogFid(blog.getBlogFid());

        assertNotNull(flag);
        assertTrue(flag);
    }

    @Test
    @DisplayName("블로그 업데이트")
    void updateBlog(){
        blog.update(
                "updateName",
                "updateNickname",
                "updateDescription",
                true
                );

        blogRepository.save(blog);
        Optional<Blog> findBlog = blogRepository.findBlogsByBlogFid("testFid");

        assertTrue(findBlog.isPresent());
        assertAll(
                ()->assertEquals("updateName", findBlog.get().getBlogName()),
                ()->assertEquals("updateNickname", findBlog.get().getBlogMbNickname()),
                ()->assertEquals("updateDescription", findBlog.get().getBlogDescription()),
                ()->assertTrue(findBlog.get().getBlogIsPublic()),
                ()->assertNotNull(findBlog.get().getUpdatedAt())
        );

    }

    @Test
    @Transactional
    @DisplayName("블로그 삭제")
    void removeBlog(){
        blogRepository.deleteBlogsByBlogFid(blog.getBlogFid());

        Optional<Blog> findBlog = blogRepository.findBlogsByBlogFid("testFid");

        assertTrue(findBlog.isEmpty());

    }



    @Test
    @DisplayName("커스텀 블로그 - 멤버번호로 메인블로그 대표아이디 찾기")
    void findBlogFidFromMainBlog(){
        // #TODO A-1 블로그매핑 구현하고 하기
    }

}