package com.nhnacademy.blog.topic.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class TopicResponse {
    private final Integer topicId;
    private Integer topicPid;
    private String topicName;
    private Integer topicSec;
    private List<TopicResponse> childrenTopics = new ArrayList<>();
}