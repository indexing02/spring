package com.example.spring.exception;

import org.springframework.http.HttpStatus;

public enum UserExceptionMessage {
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "요청 파라미터의 타입이 올바르지 않습니다."),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "잘못된 요청 인자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    USER_ALREADY_DELETED(HttpStatus.CONFLICT, "이미 삭제된 유저입니다."),
    HANDLE_GENERAL(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 오류가 발생했습니다. 관리자에게 문의하세요.");

    private final HttpStatus status;
    private final String message;

    UserExceptionMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
