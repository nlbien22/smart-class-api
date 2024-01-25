-- V2__create_users_table.sql

CREATE TABLE IF NOT EXISTS "users" (
    "id" SERIAL PRIMARY KEY,
    "full_name" VARCHAR(255) NOT NULL CHECK (LENGTH("full_name") > 0),
    "phone_number" VARCHAR,
    "email" VARCHAR NOT NULL CHECK (LENGTH("email") BETWEEN 3 AND 50),
    "password" VARCHAR(255) NOT NULL CHECK (LENGTH("password") > 0),
    "role" user_role,
    "provider" user_provider,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
)