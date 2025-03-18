package com.nhnacademy.gateway.filter;


import com.nhnacademy.gateway.security.JwtProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtProvider jwtProvider;
    private final List<String> allowedPaths;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        this.allowedPaths = List.of("/api/auth/login", "/api/auth/register", "/login", "/register");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 요청 정보 추출
        ServerHttpRequest request = exchange.getRequest(); // Spring WebFlux에서 HTTP 요청 정보를 담고 있는 객체
        String path = request.getURI().getPath(); // 요청 URL에서 경로 부분만 추출 ("/api/auth/login")

        // 2. 인증 예외 경로 확인
        if(isAllowedPath(path)){
            return chain.filter(exchange); // 인증 필요없는 경로면 다음 필터로 요청 전달
        }

        // 3. 정적 리소스 확인
        if (path.startsWith("/css/") || path.startsWith("/js/")) {
            return chain.filter(exchange);
        }

        // 4. JWT 토큰 추출
        String token = extractToken(request); // Authorization" 헤더에서 "Bearer " 다음에 오는 문자열을 토큰으로 추출

        // 5. 토큰 검증 및 오류 처리
        if (token == null || !jwtProvider.validateToken(token)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 6. 사용자 정보 추가
        String email = jwtProvider.getEmailFromToken(token);

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Email", email)
                .build();

        // 7. 수정된 요청 전달
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean isAllowedPath(String path) {
        return allowedPaths.stream().anyMatch(path::startsWith);
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String authHeader = authHeaders.get(0);
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7); // "Bearer " 이후의 토큰만 추출
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
