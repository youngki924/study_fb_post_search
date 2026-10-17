package com.study.fbpostsearch.repository;

import com.study.fbpostsearch.entity.PostLikeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, UUID> {
    long countByPost_Id(UUID postId);
}
