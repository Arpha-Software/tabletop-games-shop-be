CREATE TABLE IF NOT EXISTS wishlists
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY,
    user_email          BIGINT NOT NULL,
    shareable_link   VARCHAR(255),
    CONSTRAINT pk_wishlists PRIMARY KEY (id),
    CONSTRAINT uq_wishlists_user_id UNIQUE (user_email),
    CONSTRAINT uq_wishlists_shareable_link UNIQUE (shareable_link)
);

CREATE TABLE IF NOT EXISTS wishlist_products
(
    wishlist_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    CONSTRAINT pk_wishlist_products PRIMARY KEY (wishlist_id, product_id),
    CONSTRAINT fk_wishlist_products_on_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlists (id) ON DELETE CASCADE,
    CONSTRAINT fk_wishlist_products_on_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
