package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.domain.PostEditor;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import com.example.blog.request.PostEdit;
import com.example.blog.request.PostSearch;
import com.example.blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        // postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        /**
         *  PostController -> WebPostService -> Repository
         *                    PostService
         */

        // 서비스 정책에 맞는 응답 전용 클래스 생성
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
//        return postRepository.findAll(pageable).stream()
//                .map(PostResponse::new)
//                .collect(Collectors.toList());

        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        //post.edit(postEdit.getTitle(), postEdit.getContent());

        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        post.edit(postEditorBuilder.build());
    }
}
