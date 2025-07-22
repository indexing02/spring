package com.example.spring.service;

import com.example.spring.domain.User;
import com.example.spring.exception.UserException;
import com.example.spring.exception.UserExceptionMessage;
import com.example.spring.repository.JpaUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTransactionService{

    private final JpaUserRepository jpaUserRepository;

    @PersistenceContext
    private EntityManager em;

    //트랜잭션 없는 경우 -> 매번 쿼리 발생
    public void testNoTransaction(Long userId){

        validateId(userId);

        System.out.println("첫번째 조회");
        User user1 = findValidUserById(userId);
        System.out.println("user1.getName() = " + user1.getName());

        System.out.println("두번째 조회");
        User user2 = findValidUserById(userId);
        System.out.println("user2.getName() = " + user2.getName());
    }

    //트랜잭션 있는 경우 -> 1차 캐시 확인
    @Transactional
    public void testWithTransaction(Long userId){

        validateId(userId);

        System.out.println("첫번째 조회");
        User user1 = findValidUserById(userId);
        System.out.println("user1.getName() = " + user1.getName());

        System.out.println("두번째 조회");
        User user2 = findValidUserById(userId);
        System.out.println("user2.getName() = " + user2.getName());
    }

    //트랜잭션 내에서 em.clear() → 1차 캐시 제거 후 다시 조회
    @Transactional
    public void testWithTransactionClear(Long userId){

        validateId(userId);

        System.out.println("첫번째 조회");
        User user1 = findValidUserById(userId);
        System.out.println("user1.getName() = " + user1.getName());

        System.out.println("📌 EntityManager clear() 실행");
        em.clear(); // 1차 캐시 비우기

        System.out.println("두번째 조회");
        User user2 = findValidUserById(userId);
        System.out.println("user2.getName() = " + user2.getName());
    }

    //readOnly 트랜잭션 → 조회만, 캐시 O, 쓰기 불가
    @Transactional(readOnly = true)
    public void testTransactionReadOnly(Long userId) {
        System.out.println("첫 번째 조회");
        User user1 = findValidUserById(userId);
        System.out.println("user1 = " + user1.getName());

        System.out.println("두 번째 조회");
        User user2 = findValidUserById(userId);
        System.out.println("user2 = " + user2.getName());
    }



    //유효성 검사 메서드

    private void validateId(Long id) {
        if (id == null || id < 0) {
            throw new UserException(UserExceptionMessage.ILLEGAL_ARGUMENT);
        }
    }

    private User findValidUserById(Long id) {
        return jpaUserRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionMessage.USER_NOT_FOUND));
    }


}
