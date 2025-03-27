/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 25/05/2023
 */
@Configuration
public class RouteLocatorConfig {

    @Bean
    public RouteLocator myRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("blog-api",
                        p -> p.path("/api/blog/**")
                                .uri("lb://blog-api")                )
                .route("auth-api",
                        p -> p.path("/api/auth/**")
                                .uri("lb://blog-api")
                )  // AUTH-API 대신 BLOG-API로 변경
                .route("front-service",
                        p -> p.path("/register", "/login", "/", "/css/**", "/js/**", "/favicon.ico")
                                .uri("lb://front-api")
                )
                .build();
    }
}