CREATE SCHEMA IF NOT EXISTS db;

CREATE TABLE IF NOT EXISTS db.contacts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50) NOT NULL,
    birthDate DATE
);

CREATE TABLE IF NOT EXISTS db.addresses (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50),
    postalCode VARCHAR(50),
    contactId BIGINT REFERENCES db.contacts(id)
);
