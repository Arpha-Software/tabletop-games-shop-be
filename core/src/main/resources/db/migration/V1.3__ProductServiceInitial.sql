create table "product"
(
    id bigint generated always as identity,
    name varchar not null,
    category varchar not null,
    type varchar not null,
    genre varchar not null,
    player_number int not null,
    play_time int not null,
    description text not null,
    price numeric(10, 2) not null,
    rule_link varchar,

    constraint product_pk PRIMARY KEY (id)
);