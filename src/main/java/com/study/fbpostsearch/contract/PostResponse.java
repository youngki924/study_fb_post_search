package com.study.fbpostsearch.contract;

import java.util.UUID;

public record PostResponse(UUID id, String content, long likes) {
}
