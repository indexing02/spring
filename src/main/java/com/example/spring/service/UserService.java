package com.example.spring.service;

import com.example.spring.Role;
import com.example.spring.dto.User;
import com.example.spring.dto.UserDto;
import com.example.spring.dto.UserUpdateDto;
import com.example.spring.exception.UserNotFoundException;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthService userAuthService;

    public long saveUser(UserDto userDto) {
        User user = new User(null, userDto.getName(), userDto.getAge());
        return userRepository.userSave(user);
    }

    public User getUser(Long id) {
        validateId(id);
        return findValidUserById(id);
    }

    public List<User> getUsers(Long id) {

        validateId(id);
        findValidUserById(id); // 존재하지 않으면 예외 발생

        if (userAuthService.isAdmin(id)) {
            return userRepository.findAllIncludingDeleted();
        }

        return userRepository.findAll();
    }

    public void patchUpdateUser(Long id, String name) {
        validateId(id);
        User user = findValidUserById(id);
        user.setName(name);
        userRepository.updateUser(user);
    }

    public void putUpdateUser(UserUpdateDto userUpdateDto) {
        validateId(userUpdateDto.getId());
        User user = findValidUserById(userUpdateDto.getId());
        user.setName(userUpdateDto.getName());
        userRepository.updateUser(user);
    }

    public void deleteUser(Long id) {
        validateId(id);
        User user = userRepository.findByIdIgnoreDelete(id);

        if (user == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

        if (user.isDeletd()) {
            throw new IllegalStateException("이미 삭제된 유저입니다.");
        }

        user.setDeletd(true);
        userRepository.updateUser(user);
    }

    public void roleUpdate(Long id) {
        validateId(id);
        User user = findValidUserById(id);
        user.setRole(Role.ADMIN);
        userRepository.updateUser(user);
    }

    // =========================
    // ✅ 공통 유효성 검사 메서드
    // =========================

    private void validateId(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID는 음수보다 커야 합니다");
        }
    }

    private User findValidUserById(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }
        return user;
    }
}
