package com.nhnacademy.blog.category.domain;


import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.topic.domain.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories",
    indexes = {
        @Index(name = "idx_category_pid",columnList = "category_pid",unique = false),
    }
)
@Getter
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_pid", referencedColumnName = "category_id")
    @ToString.Exclude
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> childrenCategories = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false, referencedColumnName = "blog_id")
    private Blog blog;

    /**
     * - optional - 자식 엔티티가 부모 엔티티 없이 존재할 수 있습니다.
     * - 부모 엔티티가 없을 경우 외래 키 컬럼은 null이 될 수 있습니다.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "topic_id", nullable = true, referencedColumnName = "topic_id")
    private Topic topic;

    @Column(nullable = false, length = 100)
    private String categoryName;
    private Integer categorySec = 1;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Category(Category parentCategory, Blog blog, Topic topic, String categoryName, Integer categorySec) {
        this.parentCategory = parentCategory;
        this.blog = blog;
        this.topic = topic;
        this.categoryName = categoryName;
        this.categorySec = categorySec;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static Category ofNewRootCategory(Blog blog,Topic topic, String categoryName, Integer categorySec){
        return new Category(
                null,
                blog,
                topic,
                categoryName,
                categorySec
        );
    }

    public static Category ofNewSubCategory(Category parentCategory,Blog blog,Topic topic, String categoryName, Integer categorySec){
        return new Category(
                parentCategory,
                blog,
                topic,
                categoryName,
                categorySec
        );
    }

    public void addChildCategory(Category category){
        childrenCategories.add(category);
        category.setParentCategory(this);
    }

    public void removeChildCategory(Category category){
        childrenCategories.remove(category);
        category.setParentCategory(null);
    }

    public void update(Category parentCategory, Topic topic,String categoryName,Integer categorySec){
        this.parentCategory = parentCategory;
        this.topic = topic;
        this.categoryName = categoryName;
        this.categorySec = categorySec;
    }

}
