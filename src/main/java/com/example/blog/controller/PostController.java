package com.example.blog.controller;

import com.example.blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    @PostMapping("/posts")
    //public String post(@RequestParam String title, @RequestParam String content) {
    //public String post(@RequestParam Map<String, String> params) {
    //public String post(PostCreate params) {
    public String post(@RequestBody PostCreate params) {
        //log.info("title={} content={}", title, content);
        log.info("params={}", params);
        return "Hello World";
    }
}
