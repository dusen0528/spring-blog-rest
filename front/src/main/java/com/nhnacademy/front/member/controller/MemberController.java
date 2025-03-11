package com.nhnacademy.front.member.controller;

import com.nhnacademy.front.member.adaptor.MemberAdaptor;
import com.nhnacademy.front.member.dto.MemberRegisterRequest;
import com.nhnacademy.front.member.dto.MemberResponse;
import com.nhnacademy.front.topic.TopicAdaptor;
import com.nhnacademy.front.topic.dto.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberAdaptor memberAdaptor;
    private final TopicAdaptor topicAdaptor;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("memberRegisterRequest",
                new MemberRegisterRequest(null, null, null, null, null, null, "기본 카테고리"));

        ResponseEntity<List<TopicResponse>> response = topicAdaptor.getTopics();
        model.addAttribute("topics", response.getBody());
        return "registerForm";
    }

    @PostMapping(value = "/register")
    public String registerMember(@Validated @ModelAttribute MemberRegisterRequest request,
                                 BindingResult bindingResult, Model model,   RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            try {
                ResponseEntity<List<TopicResponse>> response = topicAdaptor.getTopics();
                model.addAttribute("topics", response.getBody());
            } catch (Exception e) {
                // 토픽 로딩 실패 시 처리
            }
            return "registerForm";
        }

        try {
            ResponseEntity<MemberResponse> response = memberAdaptor.registerMember(request);
            redirectAttributes.addFlashAttribute("members", response.getBody());
            return "redirect:/login";
        } catch (Exception e) {
            // 로그 기록
            model.addAttribute("error", "회원가입 처리 중 오류가 발생했습니다: " + e.getMessage());
            try {
                ResponseEntity<List<TopicResponse>> response = topicAdaptor.getTopics();
                model.addAttribute("topics", response.getBody());
            } catch (Exception ex) {
                // 토픽 로딩 실패 시 처리
            }
            return "registerForm";
        }
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "loginForm";
    }


}
