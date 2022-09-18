package com.example.blog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation": {
 *         "title": "타이틀 값이 없습니다."
 *     }
 * }
 */
@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final List<ValidationTuple> validation = new ArrayList<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage) {
        ValidationTuple tuple = new ValidationTuple(fieldName, errorMessage);
        validation.add(tuple);
    }

    @Getter
    @RequiredArgsConstructor
    private class ValidationTuple {
        private final String fieldName;
        private final String errorMessage;
    }
}
