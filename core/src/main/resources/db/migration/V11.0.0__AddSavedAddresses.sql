-- Create a table to store multiple delivery addresses for each user
CREATE TABLE IF NOT EXISTS user_delivery_addresses
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id           BIGINT                   NOT NULL,
    city              VARCHAR(255)             NOT NULL,
    street            VARCHAR(255)             NOT NULL,
    house_number      VARCHAR(255)             NOT NULL,
    flat_number       VARCHAR(255),
    department        VARCHAR(255),
    is_default        BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_user_delivery_addresses PRIMARY KEY (id),
    CONSTRAINT fk_user_delivery_addresses_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Create a table to store user payment methods
CREATE TABLE IF NOT EXISTS user_payment_methods
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id             BIGINT                   NOT NULL,
    payment_method_type VARCHAR(255)             NOT NULL, -- e.g., 'CASH', 'ONLINE'
    card_last_four      VARCHAR(4),
    is_default          BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_user_payment_methods PRIMARY KEY (id),
    CONSTRAINT fk_user_payment_methods_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);