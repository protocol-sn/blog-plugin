DELETE FROM blog_entry WHERE id = '1f93ea43-10cb-4882-837c-cfb13cd9a3a6';

INSERT INTO blog_entry(id, author, blog_title, blog_text, created_at, updated_at)
VALUES ('1f93ea43-10cb-4882-837c-cfb13cd9a3a6',
        '9563f594-018b-4319-ab2b-393951b48e10',
        'test title',
        'test text',
        now(),
        now());

DELETE FROM blog_entry WHERE id = '6a8ad25d-9003-4a24-8cd4-ce372392e125';

INSERT INTO blog_entry(id, author, blog_title, blog_text, created_at, updated_at)
VALUES ('6a8ad25d-9003-4a24-8cd4-ce372392e125',
        '0b1f190a-bd3a-41f9-bce3-3dfa821c5280',
        'second test',
        'I am a second test',
        now(),
        now());