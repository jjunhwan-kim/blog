package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import com.example.blog.request.PostSearch;
import com.example.blog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());

        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        assertEquals(postCreate.getTitle(), post.getTitle());
        assertEquals(postCreate.getContent(), post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {

        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse postResponse = postService.get(requestPost.getId());

        // then
        assertNotNull(postResponse);
        assertEquals(1L, postRepository.count());
        assertEquals(requestPost.getTitle(), postResponse.getTitle());
        assertEquals(requestPost.getContent(), postResponse.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {

        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("호돌맨 제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // when
//        List<PostResponse> posts = postService.getList(
//                PageRequest.of(0, 5, Sort.Direction.DESC, "id"));

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("호돌맨 제목 30", posts.get(0).getTitle());
        assertEquals("호돌맨 제목 26", posts.get(4).getTitle());
    }
}