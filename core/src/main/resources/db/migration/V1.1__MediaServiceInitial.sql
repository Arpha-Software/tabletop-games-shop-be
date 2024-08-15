create table "files"
(
    id          bigint generated always as identity,
    name        varchar,
    type        varchar                  not null,
    size        float                    not null,
    create_at   timestamp with time zone,
    updated_at  timestamp with time zone not null,
    target_id   bigint                   not null,
    target_type varchar                  not null,

    constraint file_pk PRIMARY KEY (id)
);