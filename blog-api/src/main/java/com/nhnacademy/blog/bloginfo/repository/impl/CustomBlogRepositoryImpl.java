package com.nhnacademy.blog.bloginfo.repository.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.repository.CustomBlogRepository;
import com.nhnacademy.blog.blogmember.domain.QBlogMemberMapping;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.nhnacademy.blog.bloginfo.domain.QBlog;
/**
 * TODO#7-4 CustomBlogRepositoryImpl 클래스는 Querydsl을 사용하여 Blog 관련 데이터를 쿼리하는 커스텀 리포지토리 구현체입니다.
 * QuerydslRepositorySupport를 확장하여 Blog 엔티티와 관련된 복잡한 쿼리를 처리할 수 있도록 합니다.
 * 이 클래스는 CustomBlogRepository 인터페이스를 구현하며, 커스텀 쿼리 메서드를 제공합니다.
 */

public class CustomBlogRepositoryImpl extends QuerydslRepositorySupport implements CustomBlogRepository {

    /**
     * 생성자에서는 Blog.class를 사용하여 QuerydslRepositorySupport를 초기화합니다.
     * 이로 인해 Blog 엔티티에 대한 쿼리를 작성할 수 있게 됩니다.
     */
    public CustomBlogRepositoryImpl() {
        super(Blog.class); // Blog 엔티티를 대상으로 쿼리 처리
    }

    /**
     * TODO#7-5 QClass
     * Q 클래스는 Querydsl에서 사용하는 쿼리 타입 클래스로,
     * JPA 엔티티와 연관된 테이블의 컬럼들을 타입 안전하게 쿼리할 수 있도록 도와주는 클래스입니다.
     * 이 클래스는 Querydsl을 사용하여 동적 쿼리를 작성할 때,
     * SQL 쿼리의 문자열을 사용하지 않고 타입에 맞는 객체를 사용하여 쿼리를 작성할 수 있게 합니다.
     * Q 클래스는 컴파일 타임에 자동으로 생성되며,
     * Q 접두사를 붙여 QBlog, QBlogMemberMapping과 같은 이름을 가집니다.
     * Q 클래스는 compile 과정에 자동으로 생성 됩니다.
     *
     * - 참고
     *  + https://www.baeldung.com/intro-to-querydsl
     */

    // QBlog는 Blog 엔티티에 대한 Querydsl의 타입 안전한 접근을 위한 객체입니다.
    QBlog qBlog = QBlog.blog;  // QBlog 객체는 Blog 엔티티에 대한 Querydsl Q 타입을 나타냅니다.

    // QBlogMemberMapping은 BlogMemberMapping 엔티티에 대한 Querydsl Q 타입을 나타냅니다.
    QBlogMemberMapping qBlogMemberMapping = QBlogMemberMapping.blogMemberMapping;

    /**
     * 특정 회원 번호(mbNo)에 해당하는 주 블로그의 blogFid 값을 조회하는 메서드입니다.
     * @param mbNo 회원 번호
     * @return 해당 회원이 소유한 주 블로그의 blogFid
     */
    @Override
    public String findBlogFidFromMainBlog(Long mbNo) {

        /**
         * TODO#7-6 Q클레스를 이용한 객체 기반의 query 작성
         */

        // JPAQuery 객체 생성: Querydsl을 사용하여 JPA 쿼리를 작성할 수 있도록 지원
        JPAQuery<String> query = new JPAQuery<>(getEntityManager());

        // BlogMemberMapping과 Blog을 조인하여 해당 회원이 소유한 주 블로그의 blogFid를 조회
        query.from(qBlogMemberMapping)  // BlogMemberMapping 테이블을 기준으로 쿼리 시작
                .join(qBlog).on(qBlogMemberMapping.blog.eq(qBlog))  // Blog 테이블과 조인
                .where(
                        // ROLE_OWNER 역할을 가진 회원을 찾고, 해당 회원의 mbNo와 일치하는 조건을 추가
                        qBlogMemberMapping.role.roleId.eq("ROLE_OWNER")
                                .and(qBlogMemberMapping.member.mbNo.eq(mbNo))
                                // blogMain이 true인 주 블로그만 선택
                                .and(qBlog.blogMain.isTrue())
                );

        // 조건에 맞는 블로그의 blogFid 값을 조회하여 반환
        String blogFid = query
                .select(qBlog.blogFid)  // Blog 엔티티의 blogFid를 조회
                .fetchOne();  // 단 하나의 결과를 반환 (주어진 조건에 맞는 블로그의 blogFid)

        return blogFid;  // 결과 반환
    }
}
