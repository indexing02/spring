package com.example.spring.service;

import com.example.spring.domain.Role;
import com.example.spring.domain.User;
import com.example.spring.dto.UserDto;
import com.example.spring.dto.UserUpdateDto;
import com.example.spring.exception.UserException;
import com.example.spring.exception.UserExceptionMessage;
import com.example.spring.repository.JpaUserRepository;  // JPA 리포지토리
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class JpaUserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;  // JPA 레포지토리 사용
    private final UserAuthService userAuthService;  // 권한 서비스 주입

    @Override
    public long saveUser(UserDto userDto) {
        User user = new User(null, userDto.getName(), userDto.getAge());
        User savedUser = userRepository.save(user);  // JPA save()
        return savedUser.getId();
    }

    @Override
    public User getUser(Long id) {
        validateId(id);
        return findValidUserById(id);
    }

    @Override
    public List<User> getUsers(Long id) {
        validateId(id);
        findValidUserById(id);

        if (userAuthService.isAdmin(id)) {
            return userRepository.findAllIncludingDeleted();
        }
        return userRepository.findAll();
    }

    @Override
    public void patchUpdateUser(Long id, String name) {
        validateId(id);
        User user = findValidUserById(id);
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public void putUpdateUser(UserUpdateDto userUpdateDto) {
        validateId(userUpdateDto.getId());
        User user = findValidUserById(userUpdateDto.getId());
        user.setName(userUpdateDto.getName());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        validateId(id);
        User user = userRepository.findByIdIgnoreDelete(id)
                .orElseThrow(() -> new UserException(UserExceptionMessage.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new UserException(UserExceptionMessage.USER_ALREADY_DELETED);
        }

        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void roleUpdate(Long id) {
        validateId(id);
        User user = findValidUserById(id);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }


    //유효성 검사 메서드

    private void validateId(Long id) {
        if (id == null || id < 0) {
            throw new UserException(UserExceptionMessage.ILLEGAL_ARGUMENT);
        }
    }

    private User findValidUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionMessage.USER_NOT_FOUND));
    }
}
