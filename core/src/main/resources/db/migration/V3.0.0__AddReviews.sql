-- Create the new reviews table
CREATE TABLE IF NOT EXISTS reviews
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    rating      INTEGER                  NOT NULL,
    comment     TEXT,
    product_id  BIGINT                   NOT NULL,
    user_id     BIGINT                   NOT NULL,
    username    VARCHAR(255)             NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_reviews PRIMARY KEY (id),
    CONSTRAINT fk_reviews_on_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT uq_reviews_user_product UNIQUE (user_id, product_id) -- Ensures a user can only review a product once
);

-- Add denormalized columns to the products table for performance
ALTER TABLE products
    ADD COLUMN average_rating DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    ADD COLUMN review_count INTEGER NOT NULL DEFAULT 0;

