package com.study.fbpostsearch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(name = "post_keywords", uniqueConstraints = {
        @UniqueConstraint(name = "uq_post_keywords_keyword_post_id", columnNames = {"keyword", "post_id"})
})
public class PostKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "text")
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    protected PostKeywordEntity() {
    }

    public PostKeywordEntity(String keyword, PostEntity post) {
        this.keyword = keyword;
        this.post = post;
    }

    public UUID getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public PostEntity getPost() {
        return post;
    }
}
