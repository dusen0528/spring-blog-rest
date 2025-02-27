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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getMember() throws Exception {
        MemberResponse memberResponse = new MemberResponse(
                1L,
                "marco@nhnacademy.com",
                "마르코",
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
                .andExpect(jsonPath("$.email").value("marco@nhnacademy.com"))
                .andExpect(jsonPath("$.name").value("마르코"))
                .andExpect(jsonPath("$.mobile").value("01011112222"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.withdrawalAt").isEmpty())
                .andDo(print());

    }

    @Test
    void memberNotFound() throws Exception {
        MemberResponse memberResponse = new MemberResponse(
                1L,
                "marco@nhnacademy.com",
                "마르코",
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
                .andExpect(jsonPath("$.email").value("marco@nhnacademy.com"))
                .andExpect(jsonPath("$.name").value("마르코"))
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