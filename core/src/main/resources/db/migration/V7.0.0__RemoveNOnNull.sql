ALTER TABLE products
    ALTER COLUMN average_rating DROP NOT NULL;

ALTER TABLE products
    ALTER COLUMN review_count DROP NOT NULL;