package com.nhnacademy.front.auth.adaptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "authAdaptor", url = "${api.blog.url}", path = "/api/auth")
public interface AuthAdaptor {
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest);

}
