DROP TYPE IF EXISTS roles;
CREATE TYPE roles AS ENUM(
    'user',
    'admin'
);

CREATE TABLE users(
    id VARCHAR PRIMARY KEY ,
    username VARCHAR NOT NULL,
    role roles NOT NULL
)