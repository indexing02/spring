package com.example.spring.exception;

public class UserException extends RuntimeException {

    private final UserExceptionMessage userExceptionMessage;

    public UserException(UserExceptionMessage userExceptionMessage) {
        super(userExceptionMessage.getMessage());
        this.userExceptionMessage = userExceptionMessage;
    }

    public UserExceptionMessage getUserExceptionMessage() {
        return userExceptionMessage;
    }
}
