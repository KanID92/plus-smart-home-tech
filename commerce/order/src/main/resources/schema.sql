DROP TABLE if exists orders CASCADE;
DROP TABLE if exists addresses CASCADE;

CREATE TABLE IF NOT EXISTS orders (

    order_id uuid DEFAULT gen_random_uuid(),
    shopping_cart_id uuid NOT NULL,
    payment_id uuid,
    delivery_id uuid,
    state varchar(50),
    address_id uuid,

    CONSTRAINT orders_PK
        PRIMARY KEY (order_id)
);

