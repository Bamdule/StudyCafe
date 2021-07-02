package com.bamdule.studycafe.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author kim
 *
 * 에러 코드를 관리하는 enum
 */
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "NOT FOUND USER", "존재하지 않는 회원입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, null, "Invalid Request Data"),
    STANDARD_RESOLUTION_CHANGE_ERROR(HttpStatus.BAD_REQUEST, "STANDARD_RESOLUTION_CHANGE_ERROR", "기준 해상도를 변경할 경우 기준 이미지를 다시 등록해야합니다."),
    NOT_IMAGE_FILE(HttpStatus.BAD_REQUEST, "NOT_IMAGE_FILE_ERROR", "해당 파일은 이미지 파일이 아닙니다."),
    NOT_AUDIO_FILE(HttpStatus.BAD_REQUEST, "NOT_AUDIO_FILE_ERROR", "해당 파일은 오디오 파일이 아닙니다."),
    NOT_EXIST_IMAGE_FILE(HttpStatus.BAD_REQUEST, "NOT_EXIST_IMAGE_FILE", "이미지 파일이 존재하지 않습니다."),
    JSON_PARSING_ERROR(HttpStatus.BAD_REQUEST, "JSON_PARSING_ERROR", "JSON 파싱 중 에러가 발생했습니다.");

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
