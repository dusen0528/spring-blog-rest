package com.nhnacademy.front.topic;

import com.nhnacademy.front.topic.dto.TopicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "blog-api", url = "${api.blog.url}")
public interface TopicAdaptor {
    @GetMapping("/api/blog/topics")
    ResponseEntity<List<TopicResponse>> getTopics();
}
