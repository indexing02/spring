package com.example.spring.controller;

import com.example.spring.dto.User;
import com.example.spring.dto.UserDto;
import com.example.spring.dto.UserUpdateDto;
import com.example.spring.service.UserAuthService;
import com.example.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor  // final 필드를 자동으로 생성자 주입
public class UserController {

    private final UserService userService;
    private final UserAuthService userAuthService;

    @PostMapping("/api/user3")
    public String saveUser(@RequestBody UserDto userDto) {
        Long userId = userService.saveUser(userDto); // ✅ 서비스 통해 저장
        System.out.println("id: " + userId);
        System.out.println("이름: " + userDto.getName());
        System.out.println("나이: " + userDto.getAge());
        return "저장 완료! ID: " + userId;
    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity<String> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok("이름: " + user.getName() + ", 나이: " + user.getAge());
    }


    @GetMapping("/api/users/{userId}")
    public List<User> getUsers(@PathVariable Long userId) {
        return userService.getUsers(userId);
    }

    @PutMapping("/api/users/{userId}")
    public String putUpdateUser(@RequestBody UserUpdateDto userUpdateDto) {
        System.out.println("put 수정전 : " + userUpdateDto.getId() + ", " + userService.getUser(userUpdateDto.getId()).getName());
        userService.putUpdateUser(userUpdateDto);
        System.out.println("put 수정후 : " + userUpdateDto.getId() + ", " + userUpdateDto.getName());
        return "수정 완료 (PUT)";
    }

    @PatchMapping("/api/users/{userId}")
    public String patchUpdateUser(@PathVariable Long userId, @RequestBody String name) {
        System.out.println("patch 수정전 : " + userId + ", " + userService.getUser(userId).getName());
        userService.patchUpdateUser(userId, name);
        System.out.println("patch 수정후 : " + userId + ", " + userService.getUser(userId).getName());
        return "수정 완료 (PATCH)";
    }

    @DeleteMapping("/api/users/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return "ID: " + userId + " 삭제 완료";
        }catch (IllegalArgumentException e) {
            return e.getMessage(); //유저가 존재하지 않습니다
        }catch (IllegalStateException e) {
            return e.getMessage(); //이미 삭제된 유저입니다
        }
    }

    @PatchMapping("/api/admin/{userId}")
    public String admin(@PathVariable Long userId) {
        try{
            userService.roleUpdate(userId);
            return "ADMIN으로 지정 되었습니다.";
        }catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/api/role/{userId}")
    public String role(@PathVariable Long userId) {
        try {
            return userAuthService.roleCheck(userId);
        }catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }








}
