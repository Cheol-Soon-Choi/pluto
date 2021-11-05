package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.Role;
import com.ccs.pluto.models.dto.MemberFormDto;
import com.ccs.pluto.models.entity.Member;
import com.ccs.pluto.models.entity.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMember() {
        //given
        String name = "김종국";
        String email = "gym@love.com";
        String address = "health";
        String password = "1234";

        MemberFormDto memberFormDto = MemberFormDto.builder()
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .build();

        memberService.saveMember(memberFormDto, passwordEncoder);

        //when
        List<Member> memberList = memberRepository.findAll();

        for (Member member : memberList) {
            System.out.println(member.toString());
        }

        //then
        Member member = memberList.get(0);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
        assertThat(member.getAddress()).isEqualTo(address);
        assertThat(member.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("중복회원가입 테스트")
    public void duplicateSaveMember() {
        //given
        String name = "김종국";
        String email = "gym@love.com";
        String address = "health";
        String password = "1234";

        MemberFormDto memberFormDto1 = MemberFormDto.builder()
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .build();
        MemberFormDto memberFormDto2 = MemberFormDto.builder()
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .build();

        //when
        try {
            memberService.saveMember(memberFormDto1, passwordEncoder);
            memberService.saveMember(memberFormDto2, passwordEncoder);
        }

        //then
        catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 가입된 회원입니다.");
        }
    }
}