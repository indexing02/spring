package com.example.spring.service;

import com.example.spring.domain.Role;
import com.example.spring.domain.User;
import com.example.spring.dto.UserDto;
import com.example.spring.dto.UserUpdateDto;
import com.example.spring.exception.UserException;
import com.example.spring.exception.UserExceptionMessage;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 현재 @Primary 설정된 JpaUserAuthServiceImpl이 주입됨
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
            throw new UserException(UserExceptionMessage.USER_NOT_FOUND);
        }

        if (user.isDeleted()) {
            throw new UserException(UserExceptionMessage.USER_ALREADY_DELETED);
        }

        user.setDeleted(true);
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
            throw new UserException(UserExceptionMessage.ILLEGAL_ARGUMENT);
        }
    }

    private User findValidUserById(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new UserException(UserExceptionMessage.USER_NOT_FOUND);
        }
        return user;
    }
}
