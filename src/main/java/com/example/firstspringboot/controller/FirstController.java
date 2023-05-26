package com.example.firstspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "zerone");
        // 응답 페이지 설정
        return "greetings"; // templates/greetings.mustache -> 브라우저로 전송
    }
}
