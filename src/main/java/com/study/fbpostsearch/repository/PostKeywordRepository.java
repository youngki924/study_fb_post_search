package com.study.fbpostsearch.repository;

import com.study.fbpostsearch.entity.PostKeywordEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostKeywordRepository extends JpaRepository<PostKeywordEntity, UUID> {
}
