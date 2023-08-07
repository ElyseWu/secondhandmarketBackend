DROP TABLE IF EXISTS favorite_items;
DROP TABLE IF EXISTS items_image;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chats;


CREATE TABLE users (
                       username      TEXT PRIMARY KEY   NOT NULL,
                       password      TEXT               NOT NULL,
                       location      TEXT,
                       enabled       BOOLEAN            NOT NULL
);

CREATE TABLE authority (
                           username      TEXT PRIMARY KEY NOT NULL,
                           authority     TEXT,
                           CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE items
(
    id            SERIAL PRIMARY KEY NOT NULL,
    username      TEXT               NOT NULL,
    name          TEXT               NOT NULL,
    price         NUMERIC            NOT NULL,
    description   TEXT               NOT NULL,
    condition     TEXT,
    posted_day    DATE,
    category      TEXT               NOT NULL,
    is_sold       BOOLEAN,
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE items_image(
    url           TEXT PRIMARY KEY   NOT NULL,
    item_id       INTEGER            NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
);


CREATE TABLE favorite_items(
    id            SERIAL PRIMARY KEY NOT NULL,
    item_id       INTEGER            NOT NULL,
    username      TEXT               NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE chats(
    id            SERIAL PRIMARY KEY NOT NULL,
    username1     TEXT               NOT NULL,
    username2     TEXT               NOT NULL
);

CREATE TABLE messages(
    id            SERIAL PRIMARY KEY NOT NULL,
    content       TEXT               NOT NULL,
    sender_name   TEXT               NOT NULL,
    receiver_name TEXT               NOT NULL,
    send_time     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    chat_id       INTEGER            NOT NULL,
    CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE
);








