package com.ccs.pluto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlutoApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//        return (args) -> {
//            String pass = "11111111";
//            Member member = Member.builder()
//                    .name("test")
//                    .email("gg@gg.gg")
//                    .password(passwordEncoder.encode(pass))
//                    .address("Galaxy")
//                    .build();
//            memberRepository.save(member);
//        };
//    }
}