package com.study.fbpostsearch.post;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final AtomicLong nextId = new AtomicLong();
    private final ConcurrentMap<Long, Post> posts = new ConcurrentHashMap<>();

    public PostResponse create(CreatePostRequest request) {
        long id = nextId.incrementAndGet();
        Post post = new Post(id, request.content(), new AtomicLong());
        posts.put(id, post);
        return post.toResponse();
    }

    public PostResponse like(long id) {
        Post post = posts.get(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }
        post.likes().incrementAndGet();
        return post.toResponse();
    }

    private record Post(long id, String content, AtomicLong likes) {
        private PostResponse toResponse() {
            return new PostResponse(id, content, likes.get());
        }
    }
}
