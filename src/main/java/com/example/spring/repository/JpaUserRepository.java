package com.example.spring.repository;

import com.example.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    // 삭제되지 않은 사용자만 조회 (기본 조회용)
    @Query("select u from User u where u.isDeleted = false")
    List<User> findAll();

    // 삭제 여부 상관없이 모든 사용자 조회 (관리자용)
    @Query("select u from User u")
    List<User> findAllIncludingDeleted();

    // 삭제 여부 상관없이 ID로 조회 (관리자용)
    @Query("select u from User u where u.id = :id")
    Optional<User> findByIdIgnoreDelete(Long id);

    // 논리 삭제되지 않은 사용자만 ID로 조회 (일반 사용자용)
    @Query("select u from User u where u.id = :id and u.isDeleted = false")
    Optional<User> findById(Long id); //

}
