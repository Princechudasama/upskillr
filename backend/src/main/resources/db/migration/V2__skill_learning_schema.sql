CREATE TABLE skills (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        description VARCHAR(500),
                        created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                        updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                        CONSTRAINT uq_skills_name UNIQUE (name)
);

CREATE TABLE user_skills (
                             id BIGSERIAL PRIMARY KEY,

                             user_id BIGINT NOT NULL,
                             skill_id BIGINT NOT NULL,

                             description VARCHAR(1000),

                             current_level VARCHAR(20) NOT NULL,
                             target_level VARCHAR(20) NOT NULL,

                             available_hours_per_week NUMERIC(5,2) NOT NULL,

                             created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                             updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                             CONSTRAINT fk_user_skills_user
                                 FOREIGN KEY (user_id)
                                     REFERENCES users(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_user_skills_skill
                                 FOREIGN KEY (skill_id)
                                     REFERENCES skills(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT uq_user_skill
                                 UNIQUE (user_id, skill_id),

                             CONSTRAINT chk_user_skills_current_level
                                 CHECK (
                                     current_level IN (
                                                       'BEGINNER',
                                                       'INTERMEDIATE',
                                                       'ADVANCED',
                                                       'EXPERT'
                                         )
                                     ),

                             CONSTRAINT chk_user_skills_target_level
                                 CHECK (
                                     target_level IN (
                                                      'BEGINNER',
                                                      'INTERMEDIATE',
                                                      'ADVANCED',
                                                      'EXPERT'
                                         )
                                     ),

                             CONSTRAINT chk_user_skills_hours
                                 CHECK (
                                     available_hours_per_week > 0
                                         AND available_hours_per_week <= 168
                                     )
);

CREATE INDEX idx_user_skills_user_id
    ON user_skills(user_id);

CREATE INDEX idx_user_skills_skill_id
    ON user_skills(skill_id);