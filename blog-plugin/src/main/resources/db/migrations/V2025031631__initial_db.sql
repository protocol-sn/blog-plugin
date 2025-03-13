DROP TABLE IF EXISTS blog_entry;

CREATE TABLE blog_entry (
    id UUID PRIMARY KEY,
    author UUID,
    blog_text TEXT,
    blog_title VARCHAR(255),
    tags VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);