DROP TABLE IF EXISTS blog_entry;

CREATE TABLE blog_entry (
    id UUID PRIMARY KEY,
    author UUID NOT NULL,
    blog_title VARCHAR(255) NOT NULL,
    blog_text TEXT NOT NULL,
    tags VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);