package com.study.fbpostsearch.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank(message = "content must not be blank")
        @Size(max = 5_000, message = "content must be at most 5000 characters")
        String content
) {
}
