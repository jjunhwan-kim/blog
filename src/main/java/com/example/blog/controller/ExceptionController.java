package com.example.blog.controller;

import com.example.blog.exception.HodologException;
import com.example.blog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        //MethodArgumentNotValidException
        /*
        Map<String, String> response = new HashMap<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            response.put(field, defaultMessage);
        }
        return response;
        */

        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HodologException.class)
    public ResponseEntity<ErrorResponse> hodologException(HodologException e) {

        int statusCode = e.getStatusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        for (Map.Entry<String, String> entry : e.getValidation().entrySet()) {
            errorResponse.addValidation(entry.getKey(), entry.getValue());
        }

        return ResponseEntity.status(statusCode)
                .body(errorResponse);
    }
}
