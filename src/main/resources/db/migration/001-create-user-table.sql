CREATE TABLE users
(
    id       CHAR(36) PRIMARY KEY NOT NULL,
    name     VARCHAR(60)          NOT NULL UNIQUE,
    password CHAR(60)             NOT NULL
);


