package com.bamdule.studycafe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;
import java.util.List;
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

//    @ExceptionHandler(LibraryException.class)
//    protected ResponseEntity<ErrorResponse> libraryException(LibraryException e) {
//
//        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
//        return getResponseEntity(
//                httpStatus,
//                e.getErrorCode().getMessage(),
//                e.getErrorCode().getCode()
//        );
//    }

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

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> exception(Exception e) {
//
//        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//
//        logger.error("", e);
//
//        return getResponseEntity(
//                httpStatus,
//                "서버 에러가 발생하였습니다. 담당 개발자에게 문의해주세요.",
//                e.toString()
//        );
//    }

//    public ResponseEntity<ErrorResponse> getResponseEntity(HttpStatus httpStatus, String message, String code) {
//        ErrorResponse response = new ErrorResponse();
//        response.setStatus(httpStatus.value());
//        response.setError(httpStatus.getReasonPhrase());
//        response.setMessage(message);
//        response.setCode(code);
//
//        return new ResponseEntity<>(response, httpStatus);
//    }
}
