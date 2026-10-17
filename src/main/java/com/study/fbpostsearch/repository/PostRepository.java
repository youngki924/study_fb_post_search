package com.study.fbpostsearch.repository;

import com.study.fbpostsearch.entity.PostEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
}
