package com.nhnacademy.front.blog.adaptor;

import com.nhnacademy.front.blog.dto.BlogResponse;
import com.nhnacademy.front.common.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "blogAdaptor", url = "${api.blog.url}", configuration = FeignClientConfig.class,
path = "/")
public interface BlogAdaptor {
    @GetMapping("api/blogs/{blogFid}")
    ResponseEntity<BlogResponse> getBlogByFid(@PathVariable String blogFid);
}
