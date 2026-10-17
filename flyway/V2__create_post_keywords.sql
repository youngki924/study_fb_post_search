CREATE TABLE post_keywords (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    keyword TEXT NOT NULL,
    post_id UUID NOT NULL REFERENCES posts(id),
    CONSTRAINT uq_post_keywords_keyword_post_id UNIQUE (keyword, post_id)
);

CREATE INDEX idx_post_keywords_post_id ON post_keywords (post_id);
