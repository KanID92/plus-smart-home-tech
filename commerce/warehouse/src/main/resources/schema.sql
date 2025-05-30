DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE IF NOT EXISTS dimensions
(
    dimension_id    UUID   PRIMARY KEY DEFAULT gen_random_uuid(),
    width           DOUBLE PRECISION NOT NULL,
    height          DOUBLE PRECISION NOT NULL,
    depth           DOUBLE PRECISION NOT NULL
);


CREATE TABLE IF NOT EXISTS warehouse_products
(
    warehouse_product_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id              UUID             NOT NULL UNIQUE,
    fragile                 BOOLEAN          NOT NULL,
    dimension_id            UUID             NOT NULL REFERENCES dimensions (dimension_id) ON DELETE CASCADE,
    weight                  DOUBLE PRECISION NOT NULL,
    quantity                BIGINT NOT NULL
);


CREATE TABLE IF NOT EXISTS order_bookings_products
(
    order_product_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id             UUID       NOT NULL,
    delivery_id          UUID,
    product_id           UUID       NOT NULL
        REFERENCES warehouse_products (product_id) ON DELETE CASCADE,
    reserved_quantity    BIGINT     NOT NULL
)