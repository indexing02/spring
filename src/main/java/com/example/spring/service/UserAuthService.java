package com.example.spring.service;

public interface UserAuthService {
    boolean isAdmin(Long id);
    String roleCheck(Long id);
}
