package com.nhnacademy.blog.role.repository;

import com.nhnacademy.blog.role.doamin.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findRoleByRoleId(String roleId);

}
