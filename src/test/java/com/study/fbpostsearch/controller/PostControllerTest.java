package com.study.fbpostsearch.controller;

import com.study.fbpostsearch.contract.CreatePostRequest;
import com.study.fbpostsearch.contract.PostResponse;
import com.study.fbpostsearch.service.PostService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    private static final UUID POST_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    void createsAndLikesAPost() throws Exception {
        when(postService.create(new CreatePostRequest("Hello world", "user-123")))
                .thenReturn(new PostResponse(POST_ID, "Hello world", 0));

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Hello world\",\"userId\":\"user-123\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/posts/" + POST_ID))
                .andExpect(jsonPath("$.id").value(POST_ID.toString()))
                .andExpect(jsonPath("$.likes").value(0));

        when(postService.like(POST_ID, "user-123"))
                .thenReturn(new PostResponse(POST_ID, "Hello world", 1));

        mockMvc.perform(post("/api/v1/posts/{id}/like", POST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"user-123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes").value(1));

        verify(postService).create(new CreatePostRequest("Hello world", "user-123"));
        verify(postService).like(POST_ID, "user-123");
    }
}
