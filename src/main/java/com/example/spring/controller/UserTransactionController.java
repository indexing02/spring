package com.example.spring.controller;

import com.example.spring.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-test")
@RequiredArgsConstructor
public class UserTransactionController {

    private final UserTransactionService userTransactionService;

    // 트랜잭션 없이 조회 테스트
    @GetMapping("/no-tx/{id}")
    public String testNoTransaction(@PathVariable("id") Long userId) {
        userTransactionService.testNoTransaction(userId);
        return "✅ 트랜잭션 없이 조회 완료";
    }

    // 트랜잭션 있는 조회 테스트
    @GetMapping("/tx/{id}")
    public String testWithTransaction(@PathVariable("id") Long userId) {
        userTransactionService.testWithTransaction(userId);
        return "✅ 트랜잭션 내 조회 완료";
    }

    // 트랜잭션 내 clear 테스트
    @GetMapping("/tx-clear/{id}")
    public String testWithTransactionClear(@PathVariable("id") Long userId) {
        userTransactionService.testWithTransactionClear(userId);
        return "✅ 트랜잭션 내 em.clear() 테스트 완료";
    }

    // readOnly 트랜잭션 조회 테스트
    @GetMapping("/readonly/{id}")
    public String testReadOnlyTransaction(@PathVariable("id") Long userId) {
        userTransactionService.testTransactionReadOnly(userId);
        return "✅ readOnly 트랜잭션 조회 완료";
    }
}
