package com.example.spring.service;

import com.example.spring.domain.Role;
import com.example.spring.domain.User;
import com.example.spring.exception.UserException;
import com.example.spring.exception.UserExceptionMessage;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    // ADMIN 여부만 판단하는 메서드 (핵심 비즈니스)
    public boolean isAdmin(Long id) {
        validateId(id);
        User user = findValidUserById(id);
        return user.getRole().equals(Role.ADMIN);
    }

    // 메시지 응답은 컨트롤러에서 해석할 수도 있음
    public String roleCheck(Long id) {
        return isAdmin(id) ? "ADMIN 인증 되었습니다" : "권한이 없습니다.";
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
