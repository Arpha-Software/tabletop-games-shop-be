-- Index for searching products by name (useful for autocomplete or search features)
CREATE INDEX idx_product_name ON products USING gin(to_tsvector('simple', name));

-- Index for filtering and sorting products by price
CREATE INDEX idx_product_price ON products(price);

-- Index for filtering products by type, category, and genre
CREATE INDEX idx_product_type_id ON products(type_id);
CREATE INDEX idx_product_category_id ON product_category(category_id);
CREATE INDEX idx_product_genre_id ON product_genre(genre_id);

