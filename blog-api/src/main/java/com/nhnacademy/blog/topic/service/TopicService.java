package com.nhnacademy.blog.topic.service;

import com.nhnacademy.blog.topic.dto.TopicResponse;

import java.util.List;

public interface TopicService {
    List<TopicResponse> getRootTopics();
    List<TopicResponse> getSubTopics(int parentTopicId);
}
