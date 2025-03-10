package com.nhnacademy.blog.controller;

import com.nhnacademy.blog.bloginfo.dto.BlogRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {
    // TODO# 9999 테스트코드 만들기

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(@RequestBody BlogRequest blogRequest){
        BlogResponse blogResponse = blogService.saveBlog(blogRequest);
        return new ResponseEntity<>(blogResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{blogFid}")
    public ResponseEntity<BlogResponse> getBlogByFid(@PathVariable String blogFid){
        BlogResponse blogResponse = blogService.getBlogByFid(blogFid);
        return ResponseEntity.ok(blogResponse);
    }

    @PutMapping("/{blogFid}")
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable String blogFid, @RequestBody BlogUpdateRequest blogUpdateRequest){
        if (!blogFid.equals(blogUpdateRequest.getBlogFid())) {
            throw new BadRequestException("URL의 blogFid와 요청 본문의 blogFid가 일치하지 않습니다");
        }

        BlogResponse blogResponse = blogService.updateBlog(blogUpdateRequest);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/{blogFid}")
    public ResponseEntity<Void> deleteBlog(@PathVariable String blogFid){
        blogService.deleteBlogByFid(blogFid);
        return ResponseEntity.noContent().build();
    }


}
