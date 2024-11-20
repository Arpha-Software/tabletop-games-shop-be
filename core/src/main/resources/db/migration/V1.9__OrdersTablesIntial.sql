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

CREATE TABLE IF NOT EXISTS delivery_details
(
    id            bigint generated always as identity,
    city          varchar not null,
    street        varchar not null,
    house_number  varchar,
    flat_number   varchar,
    department varchar,
    delivery_type varchar not null,
    delivery_price number(10,2),
    expected_delivery_date date,
    doc_number varchar,
    constraint delivery_details_pk PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS orders
(
    id bigint generated always as identity,
    user_id bigint,
    customer_details_id bigint not null,
    delivery_details_id bigint not null,

    constraint orders_pk PRIMARY KEY (id),
    constraint order_customer_details_fk FOREIGN KEY (customer_details_id) references customer_details (id),
    constraint user_order_fk FOREIGN KEY (user_id) references users (id),
    constraint delivery_details_fk FOREIGN KEY (delivery_details_id) references delivery_details (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    id         bigint generated always as identity,
    product_id bigint not null,
    quantity   int    not null,
    order_id   bigint    not null,

    constraint order_items_pk PRIMARY KEY (id),
    constraint product_order_items_fk FOREIGN KEY (product_id) references products (id),
    constraint order_order_items_fk FOREIGN KEY (order_id) references orders (id)
);
