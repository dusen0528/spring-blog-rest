package com.nhnacademy.blog.controller;


import com.nhnacademy.blog.topic.dto.TopicResponse;
import com.nhnacademy.blog.topic.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blog/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getRootTopics() {
        List<TopicResponse> topics = topicService.getRootTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{topicId}/subtopics")
    public ResponseEntity<List<TopicResponse>> getSubTopics(@PathVariable int topicId) {
        List<TopicResponse> topics = topicService.getSubTopics(topicId);
        return ResponseEntity.ok(topics);
    }

}

