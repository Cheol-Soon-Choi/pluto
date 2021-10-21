package com.ccs.pluto.controller;

import com.ccs.pluto.models.User;
import com.ccs.pluto.models.UserDto;
import com.ccs.pluto.models.UserRepository;
import com.ccs.pluto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserRepository userRepository;
    private final UserService userService;

    //회원 가입
//    @PostMapping("/users")
//    public User joinUser(@RequestBody UserDto userDto) {
//        User user = new User(userDto);
//        return userRepository.save(user);
//    }

    //회원 목록
    @GetMapping("/users")
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @PostMapping("/users/checkid")
    public int checkid(@RequestBody Map<String, Object> data) {
        String id = (String) data.get("checkid");
        return userService.checkid(id);
    }
}
