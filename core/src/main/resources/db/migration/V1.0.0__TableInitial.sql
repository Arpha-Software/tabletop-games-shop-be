BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS "users"
(
    id                          bigint generated always as identity,
    first_name                  varchar                  not null,
    last_name                   varchar,
    email                       varchar                  not null,
    phone                       varchar,
    is_subscribed_to_newsletter boolean                  not null,
    is_active                   boolean                  not null,
    is_blocked                  boolean                  not null,
    role                        varchar                  not null,
    created_at                  timestamp with time zone not null,
    updated_at                  timestamp with time zone not null,

    constraint user_pk PRIMARY KEY (id),
    constraint email_unique UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS "files"
(
    id          bigint generated always as identity,
    name        varchar,
    type        varchar                  not null,
    file_size   bigint                   not null,
    created_at  timestamp with time zone,
    updated_at  timestamp with time zone not null,
    target_id   bigint                   not null,
    target_type varchar                  not null,

    constraint file_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "audits"
(
    id bigint generated always as identity,
    action varchar not null,
    user_id bigint,
    target_id bigint not null,
    target_type varchar not null,
    created_at timestamp with time zone not null,

    constraint audit_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "product_types" (
    id bigint generated always as identity,
    name varchar not null,
    width numeric(10, 2) not null,
    height numeric(10, 2) not null,
    length numeric(10, 2) not null,
    weight numeric(10, 2) not null,

    constraint product_type_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "products"
(
    id            bigint generated always as identity,
    name          varchar                  not null,
    quantity      bigint                   not null,
    type_id       bigint                   not null,
    player_number int                      not null,
    play_time     int                      not null,
    description   text                     not null,
    price         numeric(10, 2)           not null,
    rules_link    varchar,
    width numeric(10, 2) not null,
    height numeric(10, 2) not null,
    length numeric(10, 2) not null,
    weight numeric(10, 2) not null,
    created_by    varchar                  not null,
    updated_by    varchar                  not null,
    created_at    timestamp with time zone not null,
    updated_at    timestamp with time zone not null,

    constraint product_pk PRIMARY KEY (id),
    constraint product_type_fk FOREIGN KEY (type_id) REFERENCES product_types(id)
);

CREATE TABLE IF NOT EXISTS "notifications"
(
    id                bigint generated always as identity,
    notification_type varchar                  not null,
    delivery_type     varchar                  not null,
    recipient         varchar                  not null,
    status            varchar                  not null,
    created_at        timestamp with time zone not null,

    constraint notification_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "categories"
(
    id bigint generated always as identity,
    name varchar not null,

    constraint category_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "genres"
(
    id bigint generated always as identity,
    name varchar not null,

    constraint genre_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "product_category" (
    product_id bigint not null,
    category_id bigint not null,

    constraint product_category_pk PRIMARY KEY (product_id, category_id),
    constraint product_fk FOREIGN KEY (product_id) REFERENCES products(id),
    constraint category_fk FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS "product_genre"
(
    product_id  bigint not null,
    genre_id bigint not null,

    constraint product_genre_pk PRIMARY KEY (product_id, genre_id),
    constraint product_fk FOREIGN KEY (product_id) REFERENCES products (id),
    constraint genre_fk FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE IF NOT EXISTS customer_details
(
    id           bigint generated always as identity,
    first_name   varchar not null,
    middle_name  varchar not null,
    last_name    varchar not null,
    phone_number varchar not null,
    email        varchar not null,

    constraint customer_details_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS delivery_addresses
(
    id                bigint generated always as identity,
    city              varchar,
    city_code         varchar,
    street            varchar,
    street_code       varchar,
    house_number      varchar,
    flat_number       varchar,
    department        varchar,
    department_code   varchar,

    constraint delivery_addresses_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS delivery_details
(
    id                     bigint generated always as identity,
    delivery_address_id    bigint  not null,
    delivery_type          varchar not null,
    payment_method         varchar,
    delivery_price         numeric(10, 2),
    expected_delivery_date date,
    doc_number             varchar,
    document_ref           varchar,

    constraint delivery_details_pk PRIMARY KEY (id),
    constraint delivery_addresses_fk FOREIGN KEY (delivery_address_id) references delivery_addresses (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id                  bigint generated always as identity,
    customer_details_id bigint                   not null,
    delivery_details_id bigint                   not null,
    order_status        varchar                  not null,
    created_at          timestamp with time zone not null,
    updated_at          timestamp with time zone not null,
    user_id             bigint,

    constraint orders_pk PRIMARY KEY (id),
    constraint order_customer_details_fk FOREIGN KEY (customer_details_id) references customer_details (id),
    constraint delivery_details_fk FOREIGN KEY (delivery_details_id) references delivery_details (id),
    constraint user_order_fk FOREIGN KEY (user_id) references users (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    id         bigint generated always as identity,
    product_id bigint not null,
    quantity   int    not null,
    order_id   bigint not null,

    constraint order_items_pk PRIMARY KEY (id),
    constraint product_order_items_fk FOREIGN KEY (product_id) references products (id),
    constraint order_order_items_fk FOREIGN KEY (order_id) references orders (id)
);

COMMIT TRANSACTION;

