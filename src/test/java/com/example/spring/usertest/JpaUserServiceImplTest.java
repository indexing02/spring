package com.example.spring.usertest;

import com.example.spring.domain.User;
import com.example.spring.dto.UserDto;
import com.example.spring.exception.UserException;
import com.example.spring.repository.UserRepository;
import com.example.spring.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional  // 테스트 후 롤백
public class JpaUserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("JPA로 유저 저장 후 조회")
    void saveAndFindUserTest() {
        // given
        UserDto dto = new UserDto();
        dto.setName("서연");
        dto.setAge(24);

        // when
        Long savedId = userService.saveUser(dto);
        User user = userService.getUser(savedId);

        // then
        assertThat(user.getName()).isEqualTo("서연");
        assertThat(user.getAge()).isEqualTo(24);
        assertThat(user.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("JPA로 유저 삭제 후 조회 시 예외 발생")
    void deleteUserTest() {
        // given
        UserDto dto = new UserDto();
        dto.setName("삭제유저");
        dto.setAge(30);
        Long id = userService.saveUser(dto);

        // when
        userService.deleteUser(id);

        // then
        // 삭제된 유저 조회 시 UserException 발생해야 함
        assertThatThrownBy(() -> userService.getUser(id))
                .isInstanceOf(UserException.class);
    }

    @Test
    @DisplayName("주입된 서비스와 레포지토리 구현체 확인")
    public void printInjectedBeanTypes() {
        System.out.println("userService 클래스: " + userService.getClass().getName());
        System.out.println("userRepository 클래스: " + userRepository.getClass().getName());
    }


}
