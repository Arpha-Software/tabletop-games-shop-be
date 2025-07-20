-- tabletop-games-shop/core/src/main/resources/db/migration/V10.0.0__AddBlogTables.sql

CREATE TABLE IF NOT EXISTS posts
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    title       VARCHAR(255)             NOT NULL,
    content     TEXT                     NOT NULL,
    created_by  VARCHAR(255)             NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_posts PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    post_id     BIGINT                   NOT NULL,
    user_id     BIGINT                   NOT NULL,
    username    VARCHAR(255)             NOT NULL,
    content     TEXT                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_comments_on_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);