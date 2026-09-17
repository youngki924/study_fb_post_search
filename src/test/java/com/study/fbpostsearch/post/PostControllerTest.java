package com.study.fbpostsearch.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@Import(PostService.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsAndLikesAPost() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Hello world\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/posts/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.likes").value(0));

        mockMvc.perform(post("/api/v1/posts/1/like"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes").value(1));
    }
}
