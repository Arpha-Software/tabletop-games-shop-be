create table "notifications"
(
    id                bigint generated always as identity,
    notification_type varchar                  not null,
    delivery_type     varchar                  not null,
    recipient         varchar                  not null,
    status            varchar                  not null,
    created_at        timestamp with time zone not null,

    constraint notification_pk PRIMARY KEY (id)
);

