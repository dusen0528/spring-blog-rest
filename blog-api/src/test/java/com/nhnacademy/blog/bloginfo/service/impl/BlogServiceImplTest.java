package com.nhnacademy.blog.bloginfo.service.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.dto.BlogRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.common.exception.BadRequestException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BlogServiceImplTest {

    @Mock // 블로그레포지토리는 테스트가 끝났으므로 (검증 완료) 실제 객체가 아닌 Mock 객체
    BlogRepository blogRepository;

    @InjectMocks // Mock 객체 자동 주입 후 AutoWired
    BlogServiceImpl blogService;



    @Test
    @DisplayName("블로그 저장")
    void saveBlog() {
        BlogRequest blogRequest = new BlogRequest(
                "testFid",
                true,
                "testName",
                "testNickName",
                "testDescription"
        );

        Mockito.doAnswer(invocationOnMock -> {
                  Blog blog = invocationOnMock.getArgument(0);
                  Field blogId = Blog.class.getDeclaredField("blogId");
                  Field createdAt = Blog.class.getDeclaredField("createdAt");
                  blogId.setAccessible(true);
                  blogId.set(blog, 1L);

                  createdAt.setAccessible(true);
                  createdAt.set(blog, LocalDateTime.now());
                  return null;
                })
                .when(blogRepository)
                .save(Mockito.any(Blog.class));

        BlogResponse blogResponse =  blogService.saveBlog(blogRequest);

        assertNotNull(blogResponse);

        assertAll(
                ()-> assertNotNull(blogResponse.getBlogId()),
                ()-> assertTrue(blogResponse.getCategories().isEmpty()),
                ()-> assertTrue(blogResponse.getBlogMemberMappings().isEmpty()),
                ()-> assertEquals("testFid",blogResponse.getBlogFid()),
                ()-> assertTrue(blogResponse.isBlogMain()),
                ()-> assertEquals("testName", blogResponse.getBlogName()),
                ()-> assertEquals("testNickName", blogResponse.getBlogMbNickName()),
                ()-> assertEquals("testDescription", blogResponse.getBlogDescription()),
                ()-> assertNotNull(blogResponse.getCreatedAt()),
                ()-> assertNull(blogResponse.getUpdatedAt()),
                ()-> assertTrue(blogResponse.getBlogIsPublic())
        );

    }

    @Test
    @DisplayName("블로그 대표아이디로 조회하기")
    void getBlogByFid() {
        Blog testBlog = Blog.ofNewBlog(
                "testFid",
                true,
                "testName",
                "testNickName",
                "testDescription"
        );

        // null <> <> testFid true testName testNickName testDescrption null null true


        Mockito.doAnswer(invocationOnMock -> {
            Field blogId = Blog.class.getDeclaredField("blogId");
            blogId.setAccessible(true);
            blogId.set(testBlog, 1L);
            Field createdAt = Blog.class.getDeclaredField("createdAt");
            createdAt.setAccessible(true);
            createdAt.set(testBlog, LocalDateTime.now());
            return Optional.of(testBlog);
                })
                        .when(blogRepository)
                                .findBlogsByBlogFid(Mockito.anyString());


          BlogResponse blogResponse =  blogService.getBlogByFid("testFid");

          assertNotNull(blogResponse);
            assertAll(
                    ()-> assertNotNull(blogResponse.getBlogId()),
                    ()-> assertTrue(blogResponse.getCategories().isEmpty()),
                    ()-> assertTrue(blogResponse.getBlogMemberMappings().isEmpty()),
                    ()-> assertEquals("testFid",blogResponse.getBlogFid()),
                    ()-> assertTrue(blogResponse.isBlogMain()),
                    ()-> assertEquals("testName", blogResponse.getBlogName()),
                    ()-> assertEquals("testNickName", blogResponse.getBlogMbNickName()),
                    ()-> assertEquals("testDescription", blogResponse.getBlogDescription()),
                    ()-> assertNotNull(blogResponse.getCreatedAt()),
                    ()-> assertNull(blogResponse.getUpdatedAt()),
                    ()-> assertTrue(blogResponse.getBlogIsPublic())
            );
    }

    @Test
    @DisplayName("실패 - 블로그 대표아이디로 조회하기")
    void getBlogFidFailed(){

        Mockito.when(blogRepository.findBlogsByBlogFid(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> blogService.getBlogByFid("testFid"));



    }

    @Test
    @DisplayName("블로그 업데이트")
    void updateBlog() {
        Blog testBlog = Blog.ofNewBlog(
                "testFid",
                true,
                "testName",
                "testNickName",
                "testDescription"
        );

        BlogUpdateRequest updateRequest = new BlogUpdateRequest(
                "testFid",
                "updateName",
                "updateNickname",
                "updateDescription",
                true
        );


        Mockito.doAnswer(invocationOnMock -> {
                    Field blogId = Blog.class.getDeclaredField("blogId");
                    blogId.setAccessible(true);
                    blogId.set(testBlog, 1L);
                    Field createdAt = Blog.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(testBlog, LocalDateTime.now());
                    return Optional.of(testBlog);
                })
                .when(blogRepository)
                .findBlogsByBlogFid(Mockito.anyString());


        Mockito.doAnswer(invocationOnMock -> {
                    Blog blog = invocationOnMock.getArgument(0);
                    Field updateAt = Blog.class.getDeclaredField("updatedAt");
                    updateAt.setAccessible(true);
                    updateAt.set(blog, LocalDateTime.now());
                    return null;
                })
                .when(blogRepository)
                .save(Mockito.any(Blog.class));


        BlogResponse blogResponse = blogService.updateBlog(updateRequest);


        assertNotNull(blogResponse);
        assertAll(
                ()->assertEquals("testFid", blogResponse.getBlogFid()),
                ()->assertEquals("updateName", blogResponse.getBlogName()),
                ()->assertEquals("updateNickname", blogResponse.getBlogMbNickName()),
                ()->assertEquals("updateDescription", blogResponse.getBlogDescription()),
                ()->assertTrue(blogResponse.getBlogIsPublic()),
                ()->assertNotNull(blogResponse.getUpdatedAt())
        );
    }

    @Test
    @DisplayName("존재하지 않는 블로그 삭제 시도")
    void deleteBlogByFid_NotFound() {
        Mockito.when(blogRepository.findBlogsByBlogFid("nonExistentFid")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> blogService.deleteBlogByFid("nonExistentFid"));
    }

    @Test
    @DisplayName("메인 블로그 삭제 시도")
    void deleteBlogByFid_MainBlog() {
        Blog mainBlog = Blog.ofNewBlog("mainFid", true, "Main", "Main", "Main Blog");
        Mockito.when(blogRepository.findBlogsByBlogFid("mainFid")).thenReturn(Optional.of(mainBlog));

        assertThrows(BadRequestException.class, () -> blogService.deleteBlogByFid("mainFid"));
    }

    @Test
    @DisplayName("일반 블로그 삭제")
    void deleteBlogByFid_Success() {
        Blog normalBlog = Blog.ofNewBlog("normalFid", false, "Normal", "Normal", "Normal Blog");
        Mockito.when(blogRepository.findBlogsByBlogFid("normalFid")).thenReturn(Optional.of(normalBlog));

        blogService.deleteBlogByFid("normalFid");

        Mockito.verify(blogRepository).deleteBlogsByBlogFid("normalFid");

        Mockito.when(blogRepository.findBlogsByBlogFid("normalFid")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> blogService.getBlogByFid("normalFid"));
    }
}