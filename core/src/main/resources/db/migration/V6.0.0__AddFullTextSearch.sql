ALTER TABLE products ADD COLUMN search_vector_en tsvector;
ALTER TABLE products ADD COLUMN search_vector_uk tsvector;

CREATE INDEX products_search_vector_en_idx ON products USING gin(search_vector_en);
CREATE INDEX products_search_vector_uk_idx ON products USING gin(search_vector_uk);

CREATE OR REPLACE FUNCTION update_product_search_vector_en()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.search_vector_en :=
            setweight(to_tsvector('english', coalesce(NEW.name, '')), 'A') ||
            setweight(to_tsvector('english', coalesce(NEW.description, '')), 'B');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_product_search_vector_uk()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.search_vector_uk :=
            setweight(to_tsvector('simple', coalesce(NEW.name, '')), 'A') ||
            setweight(to_tsvector('simple', coalesce(NEW.description, '')), 'B');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER product_search_vector_en_update
    BEFORE INSERT OR UPDATE ON products
    FOR EACH ROW EXECUTE FUNCTION update_product_search_vector_en();

CREATE TRIGGER product_search_vector_uk_update
    BEFORE INSERT OR UPDATE ON products
    FOR EACH ROW EXECUTE FUNCTION update_product_search_vector_uk();

UPDATE products SET
                    search_vector_en =
                        setweight(to_tsvector('english', coalesce(name, '')), 'A') ||
                        setweight(to_tsvector('english', coalesce(description, '')), 'B'),
                    search_vector_uk =
                        setweight(to_tsvector('simple', coalesce(name, '')), 'A') ||
                        setweight(to_tsvector('simple', coalesce(description, '')), 'B');
