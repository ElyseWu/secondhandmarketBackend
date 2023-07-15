DROP TABLE IF EXISTS items;

CREATE TABLE items
(
    id            SERIAL PRIMARY KEY NOT NULL,
    name          TEXT               NOT NULL,
    price         NUMERIC            NOT NULL,
    description   TEXT,
    condition     TEXT,
    posted_day    DATE,
    on_sale       BOOLEAN
);

