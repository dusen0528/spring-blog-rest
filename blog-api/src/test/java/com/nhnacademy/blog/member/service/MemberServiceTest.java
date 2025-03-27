//package com.nhnacademy.blog.member.service;
//
//import com.nhnacademy.blog.common.exception.ConflictException;
//import com.nhnacademy.blog.member.domain.Member;
//import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
//import com.nhnacademy.blog.member.dto.MemberResponse;
//import com.nhnacademy.blog.member.repository.MemberRepository;
//import com.nhnacademy.blog.member.service.impl.MemberServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//@ActiveProfiles("test")
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//    @Mock
//    MemberRepository memberRepository;
//
//    @InjectMocks
//    MemberServiceImpl memberService;
//
//
//    @Test
//    @DisplayName("멤버 회원가입")
//    void registerMember() {
//        MemberRegisterRequest request = new MemberRegisterRequest(
//                "test@naver.com",
//                "testName",
//                "password123",
//                "password123",
//                "01012345678",
//                1,
//                "testCategoryName"
//        );
//
//
//
//        Member member = Member.ofNewMember(
//                request.getMbEmail(),
//                request.getMbName(),
//                request.getMbPassword(),
//                request.getMbMobile()
//        );
//
//        Mockito.doAnswer(invocationOnMock -> {
//                    Member savedMember = invocationOnMock.getArgument(0);
//                    Field mbNoField = Member.class.getDeclaredField("mbNo");
//                    Field createdAtField = Member.class.getDeclaredField("createdAt");
//                    mbNoField.setAccessible(true);
//                    mbNoField.set(savedMember, 1L);
//
//                    createdAtField.setAccessible(true);
//                    createdAtField.set(savedMember, LocalDateTime.now());
//                    return savedMember; // save 메서드가 호출될 때 savedMember를 반환하도록 설정
//                })
//                .when(memberRepository)
//                .save(Mockito.any(Member.class));
//
//        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(member));
//
//        MemberResponse response = memberService.registerMember(request);
//
//        assertEquals(request.getMbEmail(), response.getMbEmail());
//        assertEquals(request.getMbName(), response.getMbName());
//        assertEquals(request.getMbMobile(), response.getMbMobile());
//        assertNotNull(response.getMbNo());
//        assertNotNull(response.getCreatedAt());
//        assertNull(response.getUpdatedAt());
//
//    }
//
//    @Test
//    @DisplayName("이메일 중복 회원가입")
//    void registerMemberEmailConflict(){
//        MemberRegisterRequest request = new MemberRegisterRequest(
//                "test@naver.com",
//                "testName",
//                "password123",
//                "password123",
//                "01012345678",
//                1,
//                "testCategoryName"
//        );
//
//        when(memberRepository.existsByMbEmail(any())).thenReturn(true);
//
//
//        assertThrows(ConflictException.class, ()->memberService.registerMember(request));
//    }
//
//    @Test
//    @DisplayName("전화번호 중복 회원가입")
//    void registerMemberMobileConflict(){
//
//        MemberRegisterRequest request = new MemberRegisterRequest(
//                "test@naver.com",
//                "testName",
//                "password123",
//                "password123",
//                "01012345678",
//                1,"testCategoryName"
//        );
//
//        when(memberRepository.existsByMbMobile(any())).thenReturn(true);
//
//        assertThrows(ConflictException.class,()-> memberService.registerMember(request));
//
//
//    }
//
//    @Test
//    @DisplayName("멤버 번호로 멤버 찾기")
//    void getMember() {
//        Member member = Member.ofNewMember(
//                "test@naver.com",
//                "testName",
//                "password123",
//                "01012345678"
//        );
//
//        Mockito.doAnswer(invocationOnMock -> {
//                    Member savedMember = invocationOnMock.getArgument(0);
//                    Field mbNoField = Member.class.getDeclaredField("mbNo");
//                    mbNoField.setAccessible(true);
//                    mbNoField.set(savedMember, 1L);
//                    return savedMember; // save 메서드가 호출될 때 savedMember를 반환하도록 설정
//                })
//                .when(memberRepository)
//                .save(Mockito.any(Member.class));
//
//        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(member));
//
//        MemberResponse response = memberService.getMember(1L);
//
//        assertEquals(member.getMbEmail(), response.getMbEmail());
//        assertEquals(member.getMbName(), response.getMbName());
//        assertEquals(member.getMbMobile(), response.getMbMobile());
//    }
//
//    @Test
//    @DisplayName("이메일로 멤버 찾기")
//    void getMemberByEmail() {
//
//        Member member = Member.ofNewMember(
//                "test@naver.com",
//                "testName",
//                "password123",
//                "01012345678"
//        );
//
//        Mockito.doAnswer(invocationOnMock -> {
//                    Member savedMember = invocationOnMock.getArgument(0);
//                    Field mbNoField = Member.class.getDeclaredField("mbNo");
//                    mbNoField.setAccessible(true);
//                    mbNoField.set(savedMember, 1L);
//                    return savedMember; // save 메서드가 호출될 때 savedMember를 반환하도록 설정
//                })
//                .when(memberRepository)
//                .save(Mockito.any(Member.class));
//
//        when(memberRepository.findByMbEmail(eq("test@naver.com"))).thenReturn(Optional.of(member));
//
//        MemberResponse response = memberService.getMemberByEmail("test@naver.com");
//
//        assertEquals(member.getMbEmail(), response.getMbEmail());
//        assertEquals(member.getMbName(), response.getMbName());
//        assertEquals(member.getMbMobile(), response.getMbMobile());
//    }
//}