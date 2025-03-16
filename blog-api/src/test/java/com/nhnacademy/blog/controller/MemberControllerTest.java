/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2025. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {

    }
    @Test
    void testGetMember() throws Exception {
        // Given: 테스트 데이터 준비 및 Mock 서비스 동작 정의
        MemberResponse memberResponse = new MemberResponse(
                1L, "marco@nhnacademy.com", "마르코", "01011112222",
                LocalDateTime.now(), null, null
        );
        when(memberService.getMember(anyLong())).thenReturn(memberResponse);

        // When & Then: API 호출 및 결과 검증
        mockMvc.perform(get("/api/blog/members/1")
                        .param("includeDetails", "true")
                        .header("Authorization", "Bearer token123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                // HTTP 상태 코드 검증
                .andExpect(status().isOk())

                // JSON 응답 필드 검증
                .andExpect(jsonPath("$.no").value(1L))
                .andExpect(jsonPath("$.email").value("marco@nhnacademy.com"))
                .andExpect(jsonPath("$.name").value("마르코"))
                .andExpect(jsonPath("$.mobile").value("01011112222"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.withdrawalAt").isEmpty())

                // 응답 헤더 검증
                .andExpect(header().string("Content-Type", containsString("application/json")))

                // 결과 출력
                .andDo(print())

                // 결과 반환 (필요한 경우)
                .andReturn();

        // 서비스 메서드 호출 검증
        verify(memberService).getMember(1L);
    }

    @Test
    void getMember() throws Exception {
        // Given: Mock 서비스 동작 정의
        MemberResponse memberResponse = new MemberResponse(
                1L,
                "testEmail@nhnacademy.com",
                "testName",
                "01011112222",
                LocalDateTime.now(),
                null,
                null
        );

        // When & Then: API 호출 및 결과 검증
        when(memberService.getMember(anyLong())).thenReturn(memberResponse);
        mockMvc.perform(get("/api/blog/members/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(1l))
                .andExpect(jsonPath("$.email").value("testEmail@nhnacademy.com"))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.mobile").value("01011112222"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.withdrawalAt").isEmpty())
                .andDo(print());

    }

    @Test
    void memberNotFound() throws Exception {
        MemberResponse memberResponse = new MemberResponse(
                1L,
                "testEmail@nhnacademy.com",
                "testName",
                "01011112222",
                LocalDateTime.now(),
                null,
                null
        );

        when(memberService.getMember(anyLong())).thenReturn(memberResponse);

        mockMvc.perform(get("/api/blog/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(1l))
                .andExpect(jsonPath("$.email").value("testEmail@nhnacademy.com"))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.mobile").value("01011112222"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.withdrawalAt").isEmpty())
                .andDo(print());
    }

    @Test
    void getMembers() {

    }

    @Test
    void registerMember() {

    }

    @Test
    void withdraw() {
    }

    @Test
    void updateMember() {
    }
}