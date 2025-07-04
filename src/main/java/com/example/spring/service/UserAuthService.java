package com.example.spring.service;

import com.example.spring.Role;
import com.example.spring.dto.User;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;


    public boolean isAdmin(Long id) {

        User user = userRepository.findById(id);

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        return user.getRole().equals(Role.ADMIN);
    }


    public String roleCheck(Long id) {
       if (isAdmin(id)) {
           return "ADMIN 인증 되었습니다";
       }
       return "권한이 없습니다.";
    }


}
