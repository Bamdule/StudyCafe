package com.bamdule.studycafe.exception;

import org.springframework.http.HttpStatus;

/**
 * @author kim
 * <p>
 * 예외 코드를 관리하는 enum
 */
public enum ExceptionCode {

    SEAT_ALREADY_IN_USE(HttpStatus.BAD_REQUEST, "SEAT_ALREADY_IN_USE", "이미 사용중인 자리입니다."),
    USER_ALREADY_USE(HttpStatus.BAD_REQUEST, "USER_ALREADY_USE", "유저가 이미 좌석을 사용중입니다."),
    LOGIN_FAILED(HttpStatus.FORBIDDEN, "LOGIN_FAILED", "휴대폰 번호 또는 비밀번호가 틀립니다."),
    TOKEN_EXPIRATION(HttpStatus.FORBIDDEN, "TOKEN_EXPIRATION", "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_TOKEN", "올바르지 않은 토큰입니다."),
    INVALID_TIME_EXTEND(HttpStatus.BAD_REQUEST, "INVALID_TIME_EXTEND", "시간 연장을 할 수 없습니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "NOT_FOUND_MEMBER", "존재하지 않는 회원입니다."),
    NOT_FOUND_USER_IN_USE(HttpStatus.NOT_FOUND, "NOT_FOUND_USER_IN_USE", "좌석을 사용하고 있는 유저를 찾을 수 없습니다."),
    NOT_FOUND_SEAT(HttpStatus.NOT_FOUND, "NOT_FOUND_SEAT", "존재하지 않는 좌석입니다."),

    DUPLICATED_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "DUPLICATED_PHONE_NUMBER", "이미 가입한 휴대폰 번호입니다."),
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    ExceptionCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }
}
