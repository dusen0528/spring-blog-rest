package com.nhnacademy.blog.bloginfo.repository.impl;

import com.nhnacademy.blog.bloginfo.repository.CustomBlogRepository;
import com.nhnacademy.blog.blogmember.domain.QBlogMemberMapping;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.nhnacademy.blog.bloginfo.domain.QBlog;
import org.springframework.stereotype.Repository;

@Repository
public class CustomBlogRepositoryImpl implements CustomBlogRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomBlogRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String findBlogFidFromMainBlog(Long mbNo) {
        QBlog qBlog = QBlog.blog;
        QBlogMemberMapping qBlogMemberMapping = QBlogMemberMapping.blogMemberMapping;

        JPAQuery<String> query = new JPAQuery<>(entityManager);

        return query.select(qBlog.blogFid)
                .from(qBlogMemberMapping)
                .join(qBlog).on(qBlogMemberMapping.blog.eq(qBlog))
                .where(
                        qBlogMemberMapping.role.roleId.eq("ROLE_OWNER")
                                .and(qBlogMemberMapping.member.mbNo.eq(mbNo))
                                .and(qBlog.blogMain.isTrue())
                )
                .fetchOne();
    }
}
