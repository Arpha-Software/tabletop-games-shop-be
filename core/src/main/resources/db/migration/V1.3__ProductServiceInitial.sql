create table "products"
(
    id            bigint generated always as identity,
    name          varchar                  not null,
    quantity      bigint                   not null,
    type          varchar                  not null,
    player_number int                      not null,
    play_time     int                      not null,
    description   text                     not null,
    price         numeric(10, 2)           not null,
    rules_link    varchar,
    created_by    varchar                  not null,
    updated_by    varchar                  not null,
    created_at    timestamp with time zone not null,
    updated_at    timestamp with time zone not null,

    constraint product_pk PRIMARY KEY (id)
);
