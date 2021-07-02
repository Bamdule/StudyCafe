package com.bamdule.studycafe.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    private Exception exception;

    public CustomException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, Exception exception) {
        super(exception.getMessage());
        this.errorCode = errorCode;
        this.exception = exception;
    }
}
