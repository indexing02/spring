package com.example.spring.controller;

import com.example.spring.domain.User;
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
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
        Long userId = userService.saveUser(userDto); // ✅ 서비스 통해 저장
        return ResponseEntity.ok("저장 완료! ID: " + userId);
    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity<String> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok("이름: " + user.getName() + ", 나이: " + user.getAge());
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<List<User>> getUsers(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUsers(userId));
    }

    @PutMapping("/api/users/{userId}")
    public ResponseEntity<String> putUpdateUser(@RequestBody UserUpdateDto userUpdateDto) {
        userService.putUpdateUser(userUpdateDto);
        return ResponseEntity.ok("수정완료 (PUT)");
    }

    @PatchMapping("/api/users/{userId}")
    public ResponseEntity<String> patchUpdateUser(@PathVariable Long userId, @RequestBody String name) {
        userService.patchUpdateUser(userId, name);
        return ResponseEntity.ok("수정완료 (PATCH)");
    }

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("ID: " + userId + " 삭제 완료");
    }

    @PatchMapping("/api/users/{userId}/role")
    public ResponseEntity<String> admin(@PathVariable Long userId) {
        userService.roleUpdate(userId);
        return ResponseEntity.ok("ADMIN으로 지정 되었습니다.");
    }

    @GetMapping("/api/users/{userId}/role")
    public ResponseEntity<String> role(@PathVariable Long userId) {
        return ResponseEntity.ok(userAuthService.roleCheck(userId));
    }
}
