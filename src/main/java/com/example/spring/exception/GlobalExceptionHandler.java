package com.example.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //ID가 잘못된 타입으로 들어 왔을때(컨트롤러 메서드에 진입하기 전 Spring이 매핑 과정에서 발생시키는 예외 )
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> TypeMismatch(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("유효하지 않는 ID 형식입니다.");
    }

    //UserSevice 에서 발생되는 예외
   @ExceptionHandler(UserException.class)
    public ResponseEntity<String> UserException(UserException ex){
        return ResponseEntity.status(ex.getUserExceptionMessage().getStatus())
                .body(ex.getUserExceptionMessage().getMessage());
   }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 오류: " + ex.getMessage());
    }


}
