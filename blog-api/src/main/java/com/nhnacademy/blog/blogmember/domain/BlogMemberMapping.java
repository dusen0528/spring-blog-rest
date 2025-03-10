package com.nhnacademy.blog.blogmember.domain;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.role.doamin.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_member_mappings",
    indexes = {
        @Index(name = "uk_blog_member_mapping",columnList = "mb_no, blog_id, role_id", unique = true)
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class BlogMemberMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_member_id")
    private Long blogMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mb_no", referencedColumnName = "mb_no", nullable = false)
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="blog_id", referencedColumnName = "blog_id", nullable = false)
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id", referencedColumnName = "role_id", nullable = false)
    private Role role;

    private BlogMemberMapping(Member member, Blog blog, Role role) {
        this.member = member;
        this.blog = blog;
        this.role = role;
    }

    public static BlogMemberMapping ofNewBlogMemberMapping(Member member, Blog blog, Role role) {
        return new BlogMemberMapping(member,blog,role);
    }

}
