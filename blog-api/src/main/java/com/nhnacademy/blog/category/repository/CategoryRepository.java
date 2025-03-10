package com.nhnacademy.blog.category.repository;

import com.nhnacademy.blog.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
