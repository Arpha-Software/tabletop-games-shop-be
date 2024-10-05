CREATE TABLE IF NOT EXISTS "categories"
(
    id bigint generated always as identity,
    name varchar not null,

    constraint category_pk PRIMARY KEY (id)
)