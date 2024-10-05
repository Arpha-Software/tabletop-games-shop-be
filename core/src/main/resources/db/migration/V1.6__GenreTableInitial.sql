CREATE TABLE IF NOT EXISTS "genres"
(
    id bigint generated always as identity,
    name varchar not null,

    constraint genre_pk PRIMARY KEY (id)
)