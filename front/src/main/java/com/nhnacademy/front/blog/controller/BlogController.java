package com.nhnacademy.front.blog.controller;

import com.nhnacademy.front.blog.adaptor.BlogAdaptor;
import com.nhnacademy.front.blog.dto.BlogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogAdaptor blogAdaptor;

    @GetMapping("/blogs/{blogFid}")
    public String showBlog(@PathVariable String blogFid, Model model) {
        log.debug("blogFid 제대로 들오왔나: {}", blogFid);
        try {
            log.debug("테스트 1 ");
            ResponseEntity<BlogResponse> response = blogAdaptor.getBlogByFid(blogFid);
            log.debug("테스트 2 responseBody: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                model.addAttribute("blog", response.getBody());
                log.debug("블로그 정보 로드 성공: {}", response.getBody().getBlogName());
                return "blog/view";
            } else {
                log.error("블로그 정보를 가져오는데 실패했습니다. Status: {}", response.getStatusCode());
                return "error/404";
            }
        } catch (Exception e) {
            log.error("블로그 정보를 가져오는 중 오류 발생", e);
            return "error/500";
        }
    }
}
