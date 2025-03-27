package com.nhnacademy.front.auth.controller;

import com.nhnacademy.front.auth.adaptor.AuthAdaptor;
import com.nhnacademy.front.member.adaptor.MemberAdaptor;
import com.nhnacademy.front.member.dto.MemberResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthAdaptor authAdaptor;
    private final MemberAdaptor memberAdaptor;


    @PostMapping("/api/auth/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpServletResponse response, RedirectAttributes redirectAttributes){
        try{
            // 백엔드 API에 보낼 JSON 데이터를 준비 (이메일과 비밀번호)
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);

            // AuthAdaptor를 통해 백엔드 API에 로그인 요청을 보내고 응답을 받음
            ResponseEntity<String> responseEntity = authAdaptor.login(requestBody);

            // 응답이 200번대(성공)인지 확인
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                // 응답 본문에서 JWT 토큰을 추출
                String token = responseEntity.getBody();

                // jwtToken 이라는 이름의 쿠키 생성
                Cookie cookie = new Cookie("jwtToken", token);

                // 쿠키의 경로를 "/"로 설정 (모든 페이지에서 사용 가능하게)
                cookie.setPath("/");

                // 자바스크립트에서 쿠키 접근 할 수 없게 설정 (XSS 공격 방지)
                cookie.setHttpOnly(true);

                // 쿠키를 HTTP 응답에 추가
                response.addCookie(cookie);

                // 사용자 정보 가져오기
                ResponseEntity<MemberResponse> memberResponse = memberAdaptor.getMemberByEmail(email);

                // blogFid 사용해서 리다이렉션
                String blogFid = memberResponse.getBody().getBlogFid();
                // 홈페이지로 리다이렉트
                return "redirect:/blogs/" + blogFid;

            }else{
                 // 로그인 실패시 로그인 페이지로 리다이렉트
                redirectAttributes.addAttribute("error", "true");
                return "redirect:/login";
            }
        }catch(Exception e){
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //JWT 토큰 쿠키 삭제
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);


        // 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Spring Security의 로그아웃 핸들러를 사용하여 보안 컨텍스트 정리
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?logout=true";
    }
}
