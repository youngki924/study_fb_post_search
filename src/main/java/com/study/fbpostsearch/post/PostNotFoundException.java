package com.study.fbpostsearch.post;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(long id) {
        super("Post %d was not found".formatted(id));
    }
}
