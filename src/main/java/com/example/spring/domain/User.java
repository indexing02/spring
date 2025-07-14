package com.example.spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.isDeleted = false;
        this.role = Role.USER;
    }

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isDeleted = false;
        this.role = Role.USER;
    }
}
