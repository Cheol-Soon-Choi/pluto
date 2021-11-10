package com.ccs.pluto.service;

import com.ccs.pluto.models.dto.MemberFormDto;
import com.ccs.pluto.models.entity.Member;
import com.ccs.pluto.models.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    public final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        validateDuplicateMember(memberFormDto, passwordEncoder);
        return memberRepository.save(memberFormDto.toEntity(passwordEncoder)).getId();
    }

    //이메일 중복 확인
    private void validateDuplicateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member findMember = memberRepository.findByEmail(memberFormDto.toEntity(passwordEncoder).getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
