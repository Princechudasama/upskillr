-- V1__init_schema.sql
-- Initial schema: users table backing the User entity (com.backend.entity.User)

CREATE TABLE users (
                       id          BIGSERIAL       PRIMARY KEY,
                       full_name   VARCHAR(100)    NOT NULL,
                       email       VARCHAR(150)    NOT NULL,
                       password    VARCHAR(255)    NOT NULL,
                       role        VARCHAR(20)     NOT NULL DEFAULT 'USER',
                       created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
                       updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),

                       CONSTRAINT uq_users_email UNIQUE (email),
                       CONSTRAINT chk_users_role CHECK (role IN ('USER', 'ADMIN'))
);

CREATE INDEX idx_users_email ON users (email);