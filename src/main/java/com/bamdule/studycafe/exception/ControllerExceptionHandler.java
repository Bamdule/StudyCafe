package com.bamdule.studycafe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @author kim Controller에서 발생하는 에러를 핸들링하는 클래스
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    //@Valid 검증 실패 시 Catch
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> bindException(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, Object> errors = ErrorsResponse.fieldErrors(
                httpStatus.value(),
                "Bad Request",
                "Bad Request",
                e.getBindingResult()
        );

        return ResponseEntity.status(httpStatus).body(errors);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Map<String, Object>> customException(CustomException e) {

        if (e.getException() != null) {
            logger.error("", e.getException());
        }

        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        Map<String, Object> errors = ErrorsResponse.error(
                httpStatus.value(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );

        return ResponseEntity.status(httpStatus).body(errors);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, Object>> exception(Exception e) {

        logger.error("", e);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> errors = ErrorsResponse.error(
                httpStatus.value(),
                "INTERNAL_SERVER_ERROR",
                e.getMessage()
        );

        return ResponseEntity.status(httpStatus).body(errors);
    }

}
