package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
