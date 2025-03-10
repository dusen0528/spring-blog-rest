package com.nhnacademy.blog.bloginfo.repository;

/**
 * TODO#7-2 jpaRepository와 별개로 custom repository interface를  작성 합니다.
 */
public interface CustomBlogRepository {
    String findBlogFidFromMainBlog(Long mbNo);
}
