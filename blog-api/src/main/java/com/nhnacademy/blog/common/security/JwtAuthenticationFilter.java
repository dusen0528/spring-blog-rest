package com.nhnacademy.blog.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 JWT 추출
        log.debug("필터 스타트");
        String token = extractToken(request);

        if(token!=null && jwtProvider.validateToken(token)){
            String email = jwtProvider.getEmailFromToken(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, null);

            log.debug("Authentication 생성성공!:{}", email);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 Bearer 토큰 추출
    private String extractToken(HttpServletRequest request) {
        // 1. Authorization 헤더에서 토큰 추출 시도
        log.debug("리퀘스트 : {}", request.getHeader("Authorization"));
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        log.debug("토큰 추출 성공");
        // 2. 쿠키에서 토큰 추출 시도
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    log.debug("쿠키 토큰 추출 성공 : {}", cookie.getValue() );
                    return cookie.getValue();
                }
            }
        }

        return null;

    }


}
