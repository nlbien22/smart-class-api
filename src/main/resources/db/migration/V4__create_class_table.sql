-- V4__create_classes_table.sql

CREATE TABLE IF NOT EXISTS "classes" (
    "class_id" SERIAL PRIMARY KEY,
    "class_name" VARCHAR(64) NOT NULL CHECK (LENGTH("class_name") > 0),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Juncture table for users and classes
CREATE TABLE "users_classes" (
    "id" INTEGER REFERENCES "users"("id") ON DELETE CASCADE,
    "class_id" INTEGER REFERENCES "classes"("class_id") ON DELETE CASCADE,
    PRIMARY KEY ("id", "class_id")
);