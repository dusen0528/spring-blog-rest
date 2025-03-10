package com.nhnacademy.blog.controller;

import com.nhnacademy.blog.auth.service.AuthService;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<MemberResponse> registerAuth(@RequestBody MemberRegisterRequest memberRegisterRequest){
        MemberResponse memberResponse = authService.authRegister(memberRegisterRequest);
        return new ResponseEntity<>(memberResponse, HttpStatus.CREATED);
    }


}
