package com.study.fbpostsearch.contract;

import jakarta.validation.constraints.NotBlank;

public record LikePostRequest(
        @NotBlank(message = "userId must not be blank")
        String userId
) {
}
