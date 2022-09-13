package com.example.blog.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {

        // application/x-www-form-urlencoded
        // application/json

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());

        /*
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "글 제목입니다.")
                        .param("content", "글 내용입니다."))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
        */
        /*
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
        */
    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수다.")
    void test2() throws Exception {

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": \"\", \"content\": \"내용입니다.\" }"))
//                        .content("{\"title\": null, \"content\": \"내용입니다.\" }"))
                        .content("{\"title\": null, \"content\": \"\" }"))
                .andExpect(status().isBadRequest())
                //.andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation[*].fieldName").value(Matchers.containsInAnyOrder("title", "content")))
                .andExpect(jsonPath("$.validation[*].errorMessage").value(Matchers.containsInAnyOrder("타이틀을 입력해주세요.", "컨텐츠를 입력해주세요.")))
//                .andExpect(jsonPath("$.validation.content").value("컨텐츠를 입력해주세요."))
                .andDo(print());
    }
}