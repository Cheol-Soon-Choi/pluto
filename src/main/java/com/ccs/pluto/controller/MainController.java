package com.ccs.pluto.controller;

import com.ccs.pluto.models.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    //메인 페이지
    @GetMapping("/")
    public String home() {
        return "index";
    }

    //회원가입 페이지
    @GetMapping("/members")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/member";
    }
}