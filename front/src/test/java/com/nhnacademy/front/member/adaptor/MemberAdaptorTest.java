//package com.nhnacademy.front.member.adaptor;
//
//import com.nhnacademy.front.member.dto.MemberResponse;
//import feign.FeignException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@Slf4j
//class MemberAdaptorTest {
//
//    @Autowired
//    MemberAdaptor memberAdaptor;
//
//    @Test
//    @DisplayName("member 조회")
//    void getMember() {
//        ResponseEntity<MemberResponse> memberResponseResponseEntity = memberAdaptor.getMember(1l);
//        MemberResponse memberResponse = memberResponseResponseEntity.getBody();
//        Assertions.assertNotNull(memberResponse);
//        Assertions.assertAll(
//                ()->Assertions.assertEquals(1l, memberResponse.getMbNo()),
//                ()->Assertions.assertEquals("marco1@nhnacademy.com", memberResponse.getMbEmail()),
//                ()->Assertions.assertEquals("마르코1",memberResponse.getMbName()),
//                ()->Assertions.assertEquals("011222200001", memberResponse.getMbMobile()),
//                ()->Assertions.assertNotNull(memberResponse.getCreatedAt())
//        );
//        log.debug("memberResponse:{}", memberResponse);
//    }
//
//    @Test
//    @DisplayName("1page 회원조회")
//    void getMembers() {
//        ResponseEntity<List<MemberResponse>> memberListResponseEntity = memberAdaptor.getMembers(1);
//        List<MemberResponse> memberResponseList = memberListResponseEntity.getBody();
//        log.debug("memberListResponse:{}", memberResponseList);
//        Assertions.assertEquals(5, memberResponseList.size());
//    }
//
//    @Test
//    void deleteMember() {
//
//        memberAdaptor.deleteMember(5l);
//        Assertions.assertThrows(FeignException.class,()->{
//            memberAdaptor.deleteMember(5l);
//        });
//
//    }
//
//    @Test
//    void registerMember() {
//    }
//
//    @Test
//    void withdraw() {
//    }
//
//    @Test
//    void updateMember() {
//    }
//
//}