package com.nhnacademy.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.blog.auth.service.AuthService;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


@WebMvcTest(value = {AuthController.class}, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class
})

class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    @Test
    @DisplayName("회원가입 테스트")
    void registerAuth() throws Exception {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
                "test@nhnacademy.com",
                "testName",
                "test123",
                "01012341234",
                "testFid",
                1,
                "testCategoryName"
        );

        MemberResponse memberResponse = new MemberResponse(1L,
                memberRegisterRequest.getMbEmail(),
                memberRegisterRequest.getMbName(),
                memberRegisterRequest.getMbMobile(),
                LocalDateTime.now(),
                null,
                null
                );
        Mockito.when(authService.authRegister(Mockito.any(MemberRegisterRequest.class))).thenReturn(memberResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(memberRegisterRequest);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@nhnacademy.com"))
                .andDo(print());


    }
}