create table "audits"
(
    id bigint generated always as identity,
    action varchar not null,
    user_id bigint,
    target_id bigint not null,
    target_type varchar not null,
    created_at timestamp with time zone not null,

    constraint audit_pk PRIMARY KEY (id)
);