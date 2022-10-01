package com.example.blog.controller;

import com.example.blog.request.PostCreate;
import com.example.blog.request.PostEdit;
import com.example.blog.request.PostSearch;
import com.example.blog.response.PostResponse;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
    public void post(@RequestBody @Valid PostCreate request) {
        // repository.save(params) // 가능하면 서비스 레이어를 만들어서 호출하자
        // Case1. 저장한 데이터 Entity -> response로 응답하기
        // Case2. 저장한 데이터의 Primary ID -> response로 응답하기
        //        Client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
        // Case3. 응답 필요 없음 -> 클라이언트에서 모든 Post(글) 데이터 context를 관리함
        postService.write(request);
    }

    @GetMapping("/posts/{id}")
    public PostResponse get(@PathVariable Long id) {
        return postService.get(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getLists(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{id}")
    public void edit(@PathVariable Long id, @RequestBody @Valid PostEdit request) {
        postService.edit(id, request);
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}