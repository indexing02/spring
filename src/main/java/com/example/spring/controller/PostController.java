package com.example.spring.controller;

import com.example.spring.dto.UserDto;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // final 필드를 자동으로 생성자 주입
public class PostController {

    private final UserRepository userRepository;

    @PostMapping("/api/user2")
    public String saveUserDtoToMap(@RequestBody UserDto userDto) {
        userRepository.userDtoSave(userDto);
        System.out.println("이름: " + userDto.getName());
        System.out.println("나이: " + userDto.getAge());
        return "저장 완료!";
    }

    @PostMapping("/api/user1")
    public String logUserInput(@RequestBody UserDto userDto) {
        System.out.println("이름: " + userDto.getName());
        System.out.println("나이: " + userDto.getAge());
        return "success";
    }



}
