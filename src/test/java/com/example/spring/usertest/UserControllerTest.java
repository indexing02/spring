package com.example.spring.usertest;

import com.example.spring.controller.UserController;
import com.example.spring.dto.User;
import com.example.spring.dto.UserDto;
import com.example.spring.exception.UserNotFoundException;
import com.example.spring.service.UserAuthService;
import com.example.spring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // JSON 변환기

    @MockBean
    private UserService userService;

    @MockBean
    private UserAuthService userAuthService;

   @Test
   @DisplayName("유저 저장")
    public void saveUser() throws Exception {
       UserDto userDto = new UserDto();
       userDto.setName("서연");
       userDto.setAge(24);

       given(userService.saveUser(any(UserDto.class))).willReturn(0L);

       mockMvc.perform(MockMvcRequestBuilders.post("/api/user3")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(userDto)))
               .andExpect(status().isOk())
               .andExpect(content().string("저장 완료! ID: 0"));

       Mockito.verify(userService).saveUser(any(UserDto.class));

   }

   @Test
   @DisplayName("유저 조회")
    public void getUser() throws Exception {

       User user = new User(0L,"서연",24);

       given(userService.getUser(0L)).willReturn(user);

       mockMvc.perform(MockMvcRequestBuilders.get("/api/user/0"))
               .andExpect(status().isOk())
               .andExpect(content().string("이름: 서연, 나이: 24"));

       Mockito.verify(userService).getUser(0L);

   }

    // 1. 유저 ID가 음수일 때 → IllegalArgumentException 기대
   @Test
   @DisplayName("유저 조회 실패 - 잘못된 인자 전달")
   public void getUSer_throwsIllegalArgument() throws Exception {
       Long userId = -1L;

       given(userService.getUser(userId)).willThrow(new IllegalArgumentException("ID는 음수보다 커야 합니다"));

       mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+userId))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("ID는 음수보다 커야 합니다"));
   }

    // 2. 유저 ID가 존재하지 않을 때 → UserNotFoundException 기대
   @Test
   @DisplayName("유저 조회 실패 - 존재 하지 않는 ID")
   public void getUser_throwsUserNotFound() throws Exception {
       Long userId = -999L;

       given(userService.getUser(userId)).willThrow(new UserNotFoundException("해당 유저를 찾을 수 없습니다"));

       mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+userId))
               .andExpect(status().isNotFound())
               .andExpect(content().string("해당 유저를 찾을 수 없습니다"));
   }


   //문자열 ID가 들어올 때 → MethodArgumentTypeMismatchException 기대
   @Test
   @DisplayName("유저 조회 실패 - ID 타입 불일치")
   public void getUser_throwsTypeMismatch() throws Exception {

       String userId = "abc";

       mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+userId))
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("유효하지 않는 ID 형식입니다.")));
   }



}
