DROP TABLE IF EXISTS items;

CREATE TABLE items
(
    id            SERIAL PRIMARY KEY NOT NULL,
    name          TEXT               NOT NULL,
    price         NUMERIC            NOT NULL,
    description   TEXT,
    condition     TEXT,
    posted_day    DATE,
    category      TEXT,
    on_sale       BOOLEAN
);

INSERT INTO items (name, price, description, condition, posted_day, category, on_sale)
VALUES ('iphone', 100, 'iphone xs max', 'very new', '2023-07-16','phone', true);

