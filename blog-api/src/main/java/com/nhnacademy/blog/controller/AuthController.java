package com.nhnacademy.blog.controller;

import com.nhnacademy.blog.auth.service.AuthService;
import com.nhnacademy.blog.common.security.JwtProvider;
import com.nhnacademy.blog.member.dto.MemberLoginRequest;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<MemberResponse> registerAuth(@RequestBody MemberRegisterRequest memberRegisterRequest){
        MemberResponse memberResponse = authService.authRegister(memberRegisterRequest);

        log.debug("회원가입 성공 ");
        return new ResponseEntity<>(memberResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest){
        String token = authService.login(memberLoginRequest);
        return ResponseEntity.ok(token);
    }


}
