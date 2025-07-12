package com.example.spring.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private int age;
    private boolean isDeletd;
    private Role role;

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isDeletd = false;
        this.role = Role.USER;
    }
}

