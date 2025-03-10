package com.nhnacademy.blog.bloginfo.repository;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * TODO#7-3 blogRepository를 통해서 추가로 Custom한 CustomBlogRepository 인터페이스 구현체의 메서드에 접근하기 위해서
 * 아래와 같이 CustomBlogRepository를 확장 합니다.
 */
public interface BlogRepository extends JpaRepository<Blog, Long> , CustomBlogRepository {

    Optional<Blog> findBlogsByBlogId(Long blogId);

    Optional<Blog> findBlogsByBlogFid(String blogFid);

    boolean existsBlogByBlogId(Long blogId);

    boolean existsBlogByBlogFid(String blogFid);

    void deleteBlogsByBlogFid(String blogFid);

    void removeBlogsByBlogId(Long blogId);
}
