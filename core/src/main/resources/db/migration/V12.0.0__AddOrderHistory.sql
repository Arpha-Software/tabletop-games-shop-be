CREATE TABLE IF NOT EXISTS order_status_history
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    order_id    BIGINT                   NOT NULL,
    status      VARCHAR(255)             NOT NULL,
    changed_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    notes       TEXT,

    CONSTRAINT pk_order_status_history PRIMARY KEY (id),
    CONSTRAINT fk_order_status_history_on_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

INSERT INTO order_status_history (order_id, status, changed_at, notes)
SELECT id, order_status, updated_at, 'Initial status'
FROM orders;
