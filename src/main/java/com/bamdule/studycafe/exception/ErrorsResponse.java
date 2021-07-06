package com.bamdule.studycafe.exception;

import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ErrorsResponse {
    private String status;
    private String error;
    private String timestamp;
    private String message;


    private ErrorsResponse() {
    }

    public static Map<String, Object> error(int status, String error, String message) {
        Map<String, Object> errorMap = new IdentityHashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", status);
        errorMap.put("error", error);
        errorMap.put("message", message);

        return errorMap;
    }

    public static Map<String, Object> fieldErrors(int status, String error, String message, Errors errors) {


        List<Map<String, Object>> fieldErrors = new ArrayList<>();

        errors.getFieldErrors().forEach(e -> {
            Map<String, Object> fieldError = new IdentityHashMap<>();

            fieldError.put("field", e.getField());
            fieldError.put("code", e.getCode());
            fieldError.put("defaultMessage", e.getDefaultMessage());
            if (e.getRejectedValue() != null) {
                fieldError.put("rejectedValue", e.getRejectedValue());
            }
            fieldErrors.add(fieldError);
        });

        Map<String, Object> errorMap = error(status, error, message);
        errorMap.put("errors", fieldErrors);

        return errorMap;
    }

//    public static Map<String, Object> globalErrors(int status, String error, String message, Errors errors) {
//
//        List<Map<String, Object>> globalErrors = new ArrayList<>();
//
//        errors.getGlobalErrors().forEach(e -> {
//            Map<String, Object> globalError = new IdentityHashMap<>();
//
//            globalError.put("code", e.getCode());
//            globalError.put("defaultMessage", e.getDefaultMessage());
//            globalErrors.add(globalError);
//        });
//
//        Map<String, Object> errorMap = error(status, error, message);
//        errorMap.put("errors", globalErrors);
//
//        return errorMap;
//    }

}
