package com.nhnacademy.blog.common.security;

import com.nhnacademy.blog.common.exception.TokenIsNotValidException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;

    // @Value 어노테이션을 사용해 설정 파일에서 값을 주입받음
    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        // Base64로 인코딩된 비밀키를 디코딩하여 Key 객체 생성
        byte[] keyBytes = secretKey.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public String generateToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1시간 유효

        return Jwts.builder()
                .setSubject(email) // 사용자 이메일을 subject로 설정
                .setIssuedAt(now) // 발급 시간 설정
                .setExpiration(validity) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // Key 객체와 알고리즘을 사용해 서명
                .compact(); // 최종적으로 문자열 형태의 JWT 반환
    }

    // 토큰 검증
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key) // 서명을 검증하기 위해 키를 설정
                    .build()   // 파서를 빌드하여 실제 검증 작업을 수행할 준비를 완료
                    .parseClaimsJws(token); // 전달받은 토큰을 파싱하여 서명 및 구조가 올바른지 확인

            return true;
        }catch(Exception e){
            return false;
        }
    }

    // 유효하지 않은 토큰 예외
    public void validateTokenOrThrow(String token) {
        //validateToken 메소드를 호출하여 토큰의 유효성을 확인
        if (!validateToken(token)) {
            throw new TokenIsNotValidException("유효하지 않은 토큰입니다");
        }
    }


    //  JWT에서 사용자 이메일(또는 다른 식별 정보)을 추출
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 서명을 검증하기 위해 키를 설정
                .build() // 파서를 빌드하여 실제 검증 작업을 수행할 준비 완료
                .parseClaimsJws(token) // 전달받은 토큰을 파싱하여 Claims 객체 반환
                .getBody() // Claims 객체에서 페이로드(body)를 가져옴
                .getSubject(); // 페이로드에서 subject 필드(사용자 이메일)를 가져옴
    }
}





