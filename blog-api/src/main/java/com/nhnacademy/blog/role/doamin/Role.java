package com.nhnacademy.blog.role.doamin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Role {

    @Id
    @Column(name="role_id",nullable = false,length = 50)
    @Comment("권한_아이디")
    private String roleId;

    @Column(nullable = false,length = 100)
    @Comment("권한_이름")
    private String roleName;

    @Column(nullable = true,length = 200)
    @Comment("권한_설정")
    private String roleDescription;

    public Role(String roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public void update(String roleName, String roleDescription){
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

}