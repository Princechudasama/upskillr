INSERT INTO skills (name, description)
VALUES
    ('Java', 'Java programming language'),
    ('Spring Boot', 'Spring Boot backend development'),
    ('PostgreSQL', 'PostgreSQL relational database'),
    ('React', 'React frontend development'),
    ('Docker', 'Containerization with Docker'),
    ('Python', 'Python programming language'),
    ('JavaScript', 'JavaScript programming language'),
    ('Git', 'Git version control'),
    ('REST API', 'RESTful API development'),
    ('Machine Learning', 'Machine learning fundamentals')
    ON CONFLICT (name) DO NOTHING;