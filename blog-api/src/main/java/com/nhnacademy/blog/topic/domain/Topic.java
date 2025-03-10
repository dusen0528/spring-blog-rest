package com.nhnacademy.blog.topic.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics",
    indexes = {
        @Index(name = "idx_topic_pid", columnList = "topic_pid", unique = false )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    @ToString.Exclude
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_pid", referencedColumnName = "topic_id")
    private Topic parentTopic;

    /**
     * orphanRemoval = true - JPA 엔티티 관계에서 자식 엔티티가 부모 엔티티와의 연관 관계가 끊어졌을 때 해당 자식 엔티티를 자동으로 삭제하도록 설정하는 데 사용됩니다.
     */
    @OneToMany(mappedBy = "parentTopic", orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Topic> childrenTopics = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String topicName;

    @Column(nullable = false)
    private Integer topicSec=1;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Topic(Topic parentTopic, String topicName, Integer topicSec) {
        this.parentTopic = parentTopic;
        this.topicName = topicName;
        this.topicSec = topicSec;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(Topic parentTopic, String topicName, Integer topicSec) {
        this.parentTopic = parentTopic;
        this.topicName = topicName;
        this.topicSec = topicSec;
    }

    public static Topic ofNewRootTopic(String topicName,Integer topicSec) {
        return new Topic(null,topicName, topicSec);
    }

    public static Topic ofNewSubTopic(Topic parentTopic, String topicName, Integer topicSec) {
        return new Topic(parentTopic,topicName, topicSec);
    }

    public void addChildTopic(Topic childTopic) {
        this.childrenTopics.add(childTopic);
        childTopic.setParentTopic(this);
    }

    public void removeChildTopic(Topic childTopic) {
        this.childrenTopics.remove(childTopic);
        childTopic.setParentTopic(null);
    }
}