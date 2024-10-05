CREATE TABLE IF NOT EXISTS "product_genre"
(
    product_id  bigint not null,
    genre_id bigint not null,

    constraint product_genre_pk PRIMARY KEY (product_id, genre_id),
    constraint product_fk FOREIGN KEY (product_id) REFERENCES products (id),
    constraint genre_fk FOREIGN KEY (genre_id) REFERENCES genres (id)
)