ALTER TABLE "graded"
    ADD COLUMN "key_code" VARCHAR(3),
    ADD COLUMN "class_name" VARCHAR(50),
    ADD COLUMN "student_name" VARCHAR(50),
    ADD COLUMN "is_error" INTEGER,
    ADD COLUMN "correct" INTEGER,
    ADD COLUMN "total" INTEGER;