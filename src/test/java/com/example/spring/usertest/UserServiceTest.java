package com.example.spring.usertest;

import com.example.spring.domain.User;
import com.example.spring.dto.UserDto;
import com.example.spring.repository.UserRepository;
import com.example.spring.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 서비스 로직 테스트")
    public void saveUserTest(){
        //given
        UserDto userDto = new UserDto();
        userDto.setName("서연");
        userDto.setAge(24);


        Mockito.when(userRepository.userSave(any(User.class))).thenReturn(0L);

        //when
        Long saveId = userService.saveUser(userDto);

        //then
        assertThat(saveId).isEqualTo(0L);


        // 실제로 userSave()가 호출되었는지 확인
        Mockito.verify(userRepository).userSave(any(User.class));

    }


    @Test
    @DisplayName("유저 조회 서비스 로직 테스트")
    public void getUserTest(){

        //given
        User user = new User(0L,"서연",24);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(user);

        //when
        User findUser = userService.getUser(0L);

        //then
        assertThat(findUser).isEqualTo(user);

        //실제로 findById()가 호출되었는지 확인
        Mockito.verify(userRepository).findById(any(Long.class));
    }

}
