DROP TABLE IF EXISTS items_image;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS users;

CREATE TABLE items
(
    id            SERIAL PRIMARY KEY NOT NULL,
    name          TEXT               NOT NULL,
    price         NUMERIC            NOT NULL,
    description   TEXT,
    condition     TEXT,
    posted_day    DATE,
    category      TEXT,
    is_sold       BOOLEAN
);

CREATE TABLE items_image(
    url           TEXT PRIMARY KEY   NOT NULL,
    item_id       INTEGER            NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
);

CREATE TABLE authority (
    username      TEXT PRIMARY KEY NOT NULL,
    authority     TEXT,
    CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);


CREATE TABLE users (
    username      TEXT PRIMARY KEY   NOT NULL,
    password      TEXT               NOT NULL,
    location      TEXT,
    enabled       BOOLEAN            NOT NULL
);
