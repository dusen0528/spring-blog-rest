package com.nhnacademy.blog.common.security;

import com.nhnacademy.blog.common.exception.TokenIsNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;
    private final String secretKey = "7c83e8ed501e28981f123d88ca87fa8a61277945c39b18a14ec8816986f96399"; // 테스트용 시크릿 키
    private final String testEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        jwtProvider = Mockito.spy(new JwtProvider(secretKey));
    }

    @Test
    @DisplayName("토큰 생성 테스트")
    void generateToken() {

        // jwt 토큰이 mock-jwt-token 반환하도록 설정
        doReturn("mock-jwt-token").when(jwtProvider).generateToken(testEmail);

        // 테스트 이메일로 토큰 생성
        String token = jwtProvider.generateToken(testEmail);

        // 생성된 토큰이 NULL이 아닌지
        assertNotNull(token);

        // 생성된 토큰이 비어있지 않은지
        assertFalse(token.isEmpty());

        // mock-jwt-token인지
        assertEquals("mock-jwt-token", token);
    }

    @Test
    @DisplayName("유효한 토큰 검증 성공")
    void validateToken_validToken_returnsTrue() {
        String token = "valid-token";
        // validateToken시 true 반환
        doReturn(true).when(jwtProvider).validateToken(token);

        // 생성된 토큰 검증 (성공 예상)
        boolean isValid = jwtProvider.validateToken(token);

        //  검증 결과가 true인지 확인
        assertTrue(isValid);
    }

    @Test
    @DisplayName("유효하지 않은 토큰 검증 실패")
    void validateToken_invalidToken_returnsFalse() {
        // 유효하지 않은 토큰 생성
        String invalidToken = "wrong-token";
        doReturn(false).when(jwtProvider).validateToken(invalidToken);

        // 유효하지 않은 토큰 검증 (실패 예상)
        boolean isValid = jwtProvider.validateToken(invalidToken);

        // 검증 결과가 false인지 확인
        assertFalse(isValid);
    }

    @Test
    @DisplayName("유효한 토큰 검증 시 예외 발생하지 않음")
    void validateTokenOrThrow_validToken_noException() {
        //  테스트 이메일로 토큰 생성
        String token = "valid-token";
        doNothing().when(jwtProvider).validateTokenOrThrow(token);

        //  예외가 발생하지 않는지 확인
        assertDoesNotThrow(() -> jwtProvider.validateTokenOrThrow(token));
    }

    @Test
    @DisplayName("유효하지 않은 토큰 검증 시 예외 발생")
    void validateTokenOrThrow_invalidToken_throwsException() {
        // 유효하지 않은 토큰 생성
        String invalidToken = "inValid-token";
        doThrow(new TokenIsNotValidException("유효하지 않은 토큰입니다.")).when(jwtProvider).validateTokenOrThrow(invalidToken);

        //  TokenIsNotValidException 예외가 발생하는지 확인
        assertThrows(TokenIsNotValidException.class, () -> jwtProvider.validateTokenOrThrow(invalidToken));
    }

    @Test
    @DisplayName("토큰에서 이메일 추출 테스트")
    void getEmailFromToken() {
        // 테스트 이메일로 토큰 생성
        String token = "valid-token";
        doReturn(testEmail).when(jwtProvider).getEmailFromToken(token);

        // 토큰에서 이메일 추출
        String email = jwtProvider.getEmailFromToken(token);

        // 추출한 이메일, 테스트 이메일 비교
        assertEquals(email, testEmail);
    }
}