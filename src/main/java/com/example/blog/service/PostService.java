package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        // postCreate -> Entity
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);

    }
}
