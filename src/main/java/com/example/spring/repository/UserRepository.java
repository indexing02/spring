package com.example.spring.repository;

import com.example.spring.domain.User;
import com.example.spring.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final Map<Long, UserDto> userDtoMap = new HashMap<>();
    private final Map<Long, User> userMap = new HashMap<>();
    private Long index = 0L;

    public void userDtoSave(UserDto user) {
        userDtoMap.put(index, user);  // 현재 index를 아이디로 저장
        index++;                   // 다음 유저를 위해 증가
    }

    public Long userSave(User user) {
        user.setId(index);
        userMap.put(index, user);
        return index++;
    }

    public User findById(Long id) {
        User user = userMap.get(id);
        if (user == null || user.isDeletd()) {
            return null; // 유저가 없거나 논리 삭제된 경우 null 리턴
        }
        return user;
    }


    // 삭제 여부 관계 없이 ID로 찾기
    public User findByIdIgnoreDelete(Long id) {
        return userMap.get(id);
    }

    public List<User> findAll() {
        return userMap.values().stream()
                .filter(user -> !user.isDeletd())
                .collect(Collectors.toList());
    }

    public List<User> findAllIncludingDeleted() {
        return userMap.values().stream().toList();
    }

    public void updateUser(User user) {
        userMap.put(user.getId(), user);  // Map에 같은 ID로 덮어쓰기 → 수정
    }

    public void deleteUser(Long id) {
        userMap.remove(id);
    }


}
