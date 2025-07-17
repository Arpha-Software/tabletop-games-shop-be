CREATE TABLE IF NOT EXISTS product_addons
(
    product_id BIGINT NOT NULL,
    addon_id  BIGINT NOT NULL,
    CONSTRAINT fk_product_addons_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_addons_addon FOREIGN KEY (addon_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT pk_product_addons PRIMARY KEY (product_id, addon_id)
);
