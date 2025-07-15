package com.example.spring.service;

import com.example.spring.domain.Role;
import com.example.spring.domain.User;
import com.example.spring.exception.UserException;
import com.example.spring.exception.UserExceptionMessage;
import com.example.spring.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class JpaUserAuthServiceImpl implements UserAuthService {

    private final JpaUserRepository userRepository;

    @Override
    public boolean isAdmin(Long id) {
        validateId(id);
        User user = findValidUserById(id);
        return user.getRole() == Role.ADMIN;
    }

    @Override
    public String roleCheck(Long id) {
        return isAdmin(id) ? "ADMIN 인증 되었습니다" : "권한이 없습니다.";
    }

    // 유효성 검사 메서드

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
