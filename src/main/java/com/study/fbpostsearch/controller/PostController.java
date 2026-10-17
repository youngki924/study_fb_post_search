package com.study.fbpostsearch.controller;

import com.study.fbpostsearch.contract.CreatePostRequest;
import com.study.fbpostsearch.contract.LikePostRequest;
import com.study.fbpostsearch.contract.PostResponse;
import com.study.fbpostsearch.service.PostService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse post = postService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + post.id())).body(post);
    }

    @PostMapping("/{id}/like")
    public PostResponse likePost(
            @PathVariable UUID id,
            @Valid @RequestBody LikePostRequest request
    ) {
        return postService.like(id, request.userId());
    }
}
