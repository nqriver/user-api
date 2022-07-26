CREATE TABLE items
(
    id       CHAR(36) PRIMARY KEY NOT NULL,
    name     VARCHAR(255)         NOT NULL,
    owner_id CHAR(36)             NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id)
);




