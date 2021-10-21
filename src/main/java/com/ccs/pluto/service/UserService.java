package com.ccs.pluto.service;

import com.ccs.pluto.models.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public int checkid(String id){
        return userRepository.countByUserid(id);
    }
}
