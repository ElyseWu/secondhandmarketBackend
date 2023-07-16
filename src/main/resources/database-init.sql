DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS items_image;


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

CREATE TABLE items_image(
    url  TEXT PRIMARY KEY NOT NULL,
    item_id INTEGER REFERENCES items (id)
);

