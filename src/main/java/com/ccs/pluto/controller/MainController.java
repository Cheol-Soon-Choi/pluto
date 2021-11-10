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

    //로그인 페이지
    @GetMapping("/login")
    public String loginMember() {
        return "member/memberLogin";
    }

    //로그인 페이지
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLogin";
    }
    
    //관리자 제품 등록 페이지
    @GetMapping("/admin/items")
    public String itemForm() {
        return "item/itemForm";
    }
}