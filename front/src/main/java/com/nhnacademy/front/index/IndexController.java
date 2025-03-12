package com.nhnacademy.front.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    // TODO : 추후에 토픽 띄워주기
    @GetMapping
    public String index(){
        return "index";
    }


}
