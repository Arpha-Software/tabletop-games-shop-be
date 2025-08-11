ALTER TABLE orders
    DROP CONSTRAINT user_order_fk;

ALTER TABLE orders
    ADD CONSTRAINT user_order_fk
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE SET NULL;
