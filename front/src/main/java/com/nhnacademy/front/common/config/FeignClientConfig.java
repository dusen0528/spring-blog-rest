package com.nhnacademy.front.common.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                Cookie[] cookies = attributes.getRequest().getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("jwtToken".equals(cookie.getName())) {
                            String token = cookie.getValue();
                            System.out.println("JWT Token found: " + token);
                            requestTemplate.header("Authorization", "Bearer " + token);
                            break;

                        }
                    }
                } else {
                    System.out.println("No cookies found in request");
                }
            } else {
                System.out.println("No request attributes found");
            }
        };
    }

}
