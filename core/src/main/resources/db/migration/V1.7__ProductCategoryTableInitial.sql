CREATE TABLE IF NOT EXISTS "product_category" (
    product_id bigint not null,
    category_id bigint not null,

    constraint product_category_pk PRIMARY KEY (product_id, category_id),
    constraint product_fk FOREIGN KEY (product_id) REFERENCES products(id),
    constraint category_fk FOREIGN KEY (category_id) REFERENCES categories(id)
)
