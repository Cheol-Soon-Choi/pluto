package com.ccs.pluto.controller;

import com.ccs.pluto.models.dto.MemberFormDto;
import com.ccs.pluto.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(SpringExtension.class)
class MemberApiControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void createMember(String email, String password) {
        MemberFormDto memberFormDto
                = MemberFormDto.builder()
                .name("유재석")
                .email(email)
                .address("서울시 강낭구 압구정")
                .password(password)
                .build();
        memberService.saveMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        //given
        String email = "test@gmail.com";
        String password = "1111";
        this.createMember(email, password);
        //when,then
        mockMvc.perform(formLogin()
                        .userParameter("email")
                        .user(email).password(password))
                .andExpect(authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        //given
        String email = "test@gmail.com";
        String password = "1111";
        this.createMember(email, password);
        //when,then
        mockMvc.perform(formLogin().userParameter("email")
                        .user(email).password("1234"))
                .andExpect(unauthenticated());
    }
}