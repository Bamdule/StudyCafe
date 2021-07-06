package com.bamdule.studycafe.exception;

import org.springframework.http.HttpStatus;

/**
 * @author kim
 * <p>
 * 에러 코드를 관리하는 enum
 */
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "NOT FOUND MEMBER", "존재하지 않는 회원입니다."),
    DUPLICATED_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "DUPLICATED_PHONE_NUMBER", "이미 가입한 휴대폰 번호입니다.."),
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

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }
}
