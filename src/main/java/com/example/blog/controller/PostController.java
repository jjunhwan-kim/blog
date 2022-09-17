package com.example.blog.controller;

import com.example.blog.request.PostCreate;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    //@PostMapping("/posts")
    //public String post(@RequestParam String title, @RequestParam String content) {
    //public String post(@RequestParam Map<String, String> params) {
    //public String post(PostCreate params) {
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
        //log.info("title={} content={}", title, content);
        // {"title": "타이틀 값이 없습니다."}
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

        log.info("params={}", params);
        return Map.of();
    }

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) {
        // repository.save(params) // 가능하면 서비스 레이어를 만들어서 호출하자

        postService.write(request);
        return Map.of();
    }
}