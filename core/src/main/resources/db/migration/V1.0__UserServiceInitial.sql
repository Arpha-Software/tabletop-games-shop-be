create table "user"
(
    id                          bigint generated always as identity,
    first_name                  varchar                  not null,
    last_name                   varchar,
    email                       varchar                  not null,
    password                    varchar                  not null,
    is_subscribed_to_newsletter boolean                  not null,
    is_active                   boolean                  not null,
    is_blocked                  boolean                  not null,
    role                        varchar                  not null,
    created_at                  timestamp with time zone not null,
    updated_at                  timestamp with time zone not null,

    constraint user_pk PRIMARY KEY (id),
    constraint email_unique UNIQUE (email)
);