package com.ccs.pluto.controller;

import com.ccs.pluto.models.dto.MemberFormDto;
import com.ccs.pluto.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/members")
    public Long newMember(@Valid @RequestBody MemberFormDto memberFormDto) {

        return memberService.saveMember(memberFormDto, passwordEncoder);
    }
}