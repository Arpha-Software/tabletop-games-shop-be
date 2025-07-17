ALTER TABLE products
    DROP COLUMN player_number,
    DROP COLUMN play_time;

ALTER TABLE products
    ADD COLUMN min_player_number INTEGER,
    ADD COLUMN max_player_number INTEGER,
    ADD COLUMN min_play_time INTEGER,
    ADD COLUMN max_play_time INTEGER,
    ADD COLUMN min_age INTEGER,
    ADD COLUMN language VARCHAR(255),
    ADD COLUMN publisher VARCHAR(255),
    ADD COLUMN author VARCHAR(255),
    ADD COLUMN bgg_rating DOUBLE PRECISION,
    ADD COLUMN complexity DOUBLE PRECISION,
    ADD COLUMN components TEXT;

CREATE TABLE IF NOT EXISTS product_mechanics
(
    product_id BIGINT NOT NULL,
    mechanic   VARCHAR(255),
    CONSTRAINT fk_product_mechanics_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
