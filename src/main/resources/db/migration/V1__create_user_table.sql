DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id VARCHAR PRIMARY KEY,
    personal_data_id VARCHAR NOT NULL REFERENCES PersonalData(id),
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    account_expiration_date TIMESTAMP,
    account_locked BOOLEAN,
    enabled BOOLEAN
);