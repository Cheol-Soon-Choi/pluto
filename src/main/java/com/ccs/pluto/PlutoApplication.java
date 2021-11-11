package com.ccs.pluto;

import com.ccs.pluto.models.entity.Member;
import com.ccs.pluto.models.entity.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PlutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlutoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            String pass = "11111111";
            Member member = Member.builder()
                    .name("test")
                    .email("gg@gg.gg")
                    .password(passwordEncoder.encode(pass))
                    .address("Galaxy")
                    .build();
            memberRepository.save(member);
        };
    }
}