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
                        p -> p.path("/api/blog/**").and()
                                .uri("lb://BLOG-API")
                )
                .route("auth-api",
                        p -> p.path("/api/auth/**")
                                .uri("lb://AUTH-API"))
                .build();


        //http://localhost:80/hello


//        return builder.routes()
//                .route("hello-service-a",
//                        p->p.path("/hello").and().weight("hello",50).uri("http://localhost:8081/")
//                        )
//                .route("hello-service-b",
//                        p->p.path("/hello").and().weight("hello",50).uri("http://localhost:8082/")
//                        )
//                .build();

//        return builder.routes()
//                .route("get_route", r -> r.path("/account")
//                        .filters(o->o.addRequestHeader("uuid", UUID.randomUUID().toString()))
//                        .uri("http://httpbin.org"))
//                .build();

        //http://httpbin.org/get


    }
}
