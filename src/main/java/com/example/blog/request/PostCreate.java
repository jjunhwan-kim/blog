package com.example.blog.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    public String content;
}
