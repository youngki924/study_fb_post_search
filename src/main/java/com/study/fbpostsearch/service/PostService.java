package com.study.fbpostsearch.service;

import com.study.fbpostsearch.exception.PostNotFoundException;
import com.study.fbpostsearch.contract.CreatePostRequest;
import com.study.fbpostsearch.contract.PostResponse;
import com.study.fbpostsearch.entity.PostEntity;
import com.study.fbpostsearch.entity.PostLikeEntity;
import com.study.fbpostsearch.repository.PostLikeRepository;
import com.study.fbpostsearch.repository.PostRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public PostResponse create(CreatePostRequest request) {
        PostEntity post = postRepository.save(new PostEntity(request.content(), request.userId()));
        return toResponse(post);
    }

    public PostResponse like(UUID id, String userId) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postLikeRepository.save(new PostLikeEntity(post, userId));
        return toResponse(post);
    }

    private PostResponse toResponse(PostEntity post) {
        long likes = postLikeRepository.countByPost_Id(post.getId());
        return new PostResponse(post.getId(), post.getContent(), likes);
    }
}
