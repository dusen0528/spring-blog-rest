package com.nhnacademy.blog.auth.service.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.common.exception.UnauthorizedException;
import com.nhnacademy.blog.common.security.JwtProvider;
import com.nhnacademy.blog.common.security.PasswordEncoder;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberLoginRequest;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.repository.RoleRepository;
import com.nhnacademy.blog.topic.domain.Topic;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    BlogRepository blogRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    TopicRepository topicRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    JwtProvider jwtProvider;

    @InjectMocks
    AuthServiceImpl authService;


    @Test
    @DisplayName("회원가입 테스트")
    void authRegister() {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
                "test@nhnacademy.com",
                "testName",
                "test123",
                "01012341234",
                "testFid",
                1,
                "testCategoryName"
        );

        Mockito.doAnswer(
                invocationOnMock -> {
                    Member member = invocationOnMock.getArgument(0);
                    Field mbNo = Member.class.getDeclaredField("mbNo");
                    mbNo.setAccessible(true);
                    mbNo.set(member, 1L);

                    Field createdAt = Member.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(member, LocalDateTime.now());

                    return null;
                }
        ).when(memberRepository).save(Mockito.any(Member.class));

        Role role = new Role("ROLE_MEMBER", "블로그_회원", "USER");
        Mockito.when(roleRepository.findRoleByRoleId(Mockito.anyString())).thenReturn(Optional.of(role));

        Mockito.doAnswer(
                invocationOnMock -> {
                    Blog blog = invocationOnMock.getArgument(0);
                    Field blogId = Blog.class.getDeclaredField("blogId");
                    blogId.setAccessible(true);
                    blogId.set(blog, 1L);
                    Field createdAt = Blog.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(blog, LocalDateTime.now());
                    return null;
                }
        ).when(blogRepository).save(Mockito.any(Blog.class));

        Topic topic = Topic.ofNewRootTopic("root", 1);
        Mockito.doAnswer(invocationOnMock -> {
                    Field topicId = Topic.class.getDeclaredField("topicId");
                    topicId.setAccessible(true);
                    topicId.set(topic, invocationOnMock.getArgument(0));
                    Field createdAt = Topic.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(topic, LocalDateTime.now());
                    return Optional.of(topic);
                })
                .when(topicRepository).findTopicByTopicId(Mockito.anyInt());

        Mockito.doAnswer(
                invocationOnMock -> {
                    Category category = invocationOnMock.getArgument(0);
                    Field categoryId = Category.class.getDeclaredField("categoryId");
                    categoryId.setAccessible(true);
                    categoryId.set(category, 1L);

                    Field createdAt = Category.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(category, LocalDateTime.now());

                    return null;
                }
        ).when(categoryRepository).save(Mockito.any(Category.class));

        MemberResponse memberResponse = authService.authRegister(memberRegisterRequest);
        assertNotNull(memberResponse);

        assertAll(
                () -> assertEquals(1L, memberResponse.getMbNo()),
                () -> assertEquals("test@nhnacademy.com", memberResponse.getMbEmail()),
                () -> assertEquals("testName", memberResponse.getMbName()),
                () -> assertEquals("01012341234", memberResponse.getMbMobile()),
                () -> assertNotNull(memberResponse.getCreatedAt()),
                () -> assertNull(memberResponse.getUpdatedAt()),
                () -> assertNull(memberResponse.getWithdrawalAt())
        );
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패 ROLE 없음")
    void givenNonExistentRoleUser_whenAuthRegister_thenThrowNotFoundException() {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
                "test@nhnacademy.com",
                "testName",
                "test123",
                "01012341234",
                "testFid",
                1,
                "testCategoryName"
        );

        Mockito.doAnswer(
                invocationOnMock -> {
                    Member member = invocationOnMock.getArgument(0);
                    Field mbNo = Member.class.getDeclaredField("mbNo");
                    mbNo.setAccessible(true);
                    mbNo.set(member, 1L);

                    Field createdAt = Member.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(member, LocalDateTime.now());

                    return null;
                }
        ).when(memberRepository).save(Mockito.any(Member.class));

        Mockito.when(roleRepository.findRoleByRoleId(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.authRegister(memberRegisterRequest));
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패 Topic 없음")
    void givenNonExistentTopic_whenAuthRegister_thenThrowNotFoundException() {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
                "test@nhnacademy.com",
                "testName",
                "test123",
                "01012341234",
                "testFid",
                1,
                "testCategoryName"
        );

        Mockito.doAnswer(
                invocationOnMock -> {
                    Member member = invocationOnMock.getArgument(0);
                    Field mbNo = Member.class.getDeclaredField("mbNo");
                    mbNo.setAccessible(true);
                    mbNo.set(member, 1L);

                    Field createdAt = Member.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(member, LocalDateTime.now());

                    return null;
                }
        ).when(memberRepository).save(Mockito.any(Member.class));

        Role role = new Role("ROLE_USER", "USER", "USER");
        Mockito.when(roleRepository.findRoleByRoleId(Mockito.anyString())).thenReturn(Optional.of(role));

        Mockito.doAnswer(
                invocationOnMock -> {
                    Blog blog = invocationOnMock.getArgument(0);
                    Field blogId = Blog.class.getDeclaredField("blogId");
                    blogId.setAccessible(true);
                    blogId.set(blog, 1L);
                    Field createdAt = Blog.class.getDeclaredField("createdAt");
                    createdAt.setAccessible(true);
                    createdAt.set(blog, LocalDateTime.now());
                    return null;
                }
        ).when(blogRepository).save(Mockito.any(Blog.class));

        Mockito.when(topicRepository.findTopicByTopicId(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.authRegister(memberRegisterRequest));
    }


    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // Given: 로그인 요청 데이터 준비
        MemberLoginRequest loginRequest = new MemberLoginRequest("test@nhnacademy.com", "password123123!");

        // Mock 설정: 데이터베이스에서 사용자 조회
        Member member = Member.ofNewMember(
                "test@nhnacademy.com",
                "TestUser",
                "password123123!",
                "01012345678"
        );

        Mockito.when(memberRepository.findByMbEmail(Mockito.anyString())).thenReturn(Optional.of(member));

        // Mock 설정: 비밀번호 비교 동작 정의 (Mock)
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        // Mock 설정: JWT 생성 동작 정의 (Mock)
        Mockito.when(jwtProvider.generateToken(Mockito.anyString())).thenReturn("mock-jwt-token");

        // When: 로그인 로직 실행
        String token = authService.login(loginRequest);

        // Then: 결과 검증
        assertNotNull(token);
        assertEquals("mock-jwt-token", token);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호 불일치")
    void loginFail() {
        // Given: 로그인 요청 데이터 준비
        MemberLoginRequest loginRequest = new MemberLoginRequest("test@nhnacademy.com", "temp");

        // Mock 설정: 데이터베이스에서 사용자 조회
        Member member = Member.ofNewMember(
                "test@nhnacademy.com",
                "TestUser",
                "password123123!",
                "01012345678"
        );
        Mockito.when(memberRepository.findByMbEmail(Mockito.anyString())).thenReturn(Optional.of(member));

        // Mock 설정: 비밀번호 불일치 상황 설정(matches가 false 반환)
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        // When & Then: 로그인 로직 실행 시 UnauthorizedException 발생 확인
        assertThrows(UnauthorizedException.class, () -> authService.login(loginRequest));

    }

}