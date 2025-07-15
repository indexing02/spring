package com.example.spring.service;

import com.example.spring.domain.User;
import com.example.spring.dto.UserDto;
import com.example.spring.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    long saveUser(UserDto userDto);
    User getUser(Long id);
    List<User> getUsers(Long id);
    void patchUpdateUser(Long id, String name);
    void putUpdateUser(UserUpdateDto userUpdateDto);
    void deleteUser(Long id);
    void roleUpdate(Long id);
}
