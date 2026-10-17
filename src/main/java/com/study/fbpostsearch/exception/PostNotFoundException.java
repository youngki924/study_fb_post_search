package com.study.fbpostsearch.exception;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(UUID id) {
        super("Post %s was not found".formatted(id));
    }
}
