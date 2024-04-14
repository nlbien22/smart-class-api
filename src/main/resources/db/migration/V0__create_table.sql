-- Create ENUM type for user_role
DO $$
    BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
        CREATE TYPE user_role AS ENUM ('ADMIN', 'TEACHER', 'STUDENT');
    END IF;
END $$;

-- Cast character varying to user_role
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'user_role'::regtype) THEN
        DROP CAST (character varying AS user_role);
    END IF;
    CREATE CAST (character varying AS user_role) WITH INOUT AS IMPLICIT;
END $$;

-- Create ENUM type for user_provider
DO $$
    BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_provider') THEN
        CREATE TYPE user_provider AS ENUM ('EMAIL', 'GOOGLE');
    END IF;
END $$;

-- Cast character varying to user_provider
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'user_provider'::regtype) THEN
        DROP CAST (character varying AS user_provider);
    END IF;
    CREATE CAST (character varying AS user_provider) WITH INOUT AS IMPLICIT;
END $$;

-- Create ENUM type for level
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'level') THEN
        CREATE TYPE level AS ENUM ('EASY', 'MEDIUM', 'HARD', 'VERY_HARD');
    END IF;
END $$;

-- Cast character varying to level
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'level'::regtype) THEN
        DROP CAST (character varying AS level);
    END IF;
    CREATE CAST (character varying AS level) WITH INOUT AS IMPLICIT;
END $$;

-- Create ENUM type for type_key
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'type_key') THEN
    CREATE TYPE type_key AS ENUM ('AUTO', 'MANUAL');
    END IF;
END $$;

-- Cast character varying to type_key
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'type_key'::regtype) THEN
    DROP CAST (character varying AS type_key);
    END IF;
    CREATE CAST (character varying AS type_key) WITH INOUT AS IMPLICIT;
END $$;

-- Create status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'status') THEN
    CREATE TYPE status AS ENUM ('PRESENT', 'LATE', 'ABSENT');
    END IF;
END $$;

-- Cast character varying to status
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'status'::regtype) THEN
    DROP CAST (character varying AS status);
    END IF;
    CREATE CAST (character varying AS status) WITH INOUT AS IMPLICIT;
END $$;
-- Create user table
CREATE TABLE IF NOT EXISTS "user" (
    "user_id" SERIAL PRIMARY KEY,
    "full_name" VARCHAR(255) NOT NULL CHECK (LENGTH("full_name") > 0),
    "phone_number" VARCHAR,
    "email" VARCHAR NOT NULL CHECK (LENGTH("email") BETWEEN 3 AND 50),
    "password" VARCHAR(255) NOT NULL CHECK (LENGTH("password") > 0),
    "user_code" INTEGER,
    "image_key" INTEGER,
    "role" user_role,
    "provider" user_provider,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);

-- Create class table
CREATE TABLE IF NOT EXISTS "class" (
    "class_id" SERIAL PRIMARY KEY,
    "class_name" VARCHAR(64) NOT NULL CHECK (LENGTH("class_name") > 0),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_class table
CREATE TABLE IF NOT EXISTS "user_class" (
    "user_id" INTEGER REFERENCES "user"("user_id") ON DELETE CASCADE,
    "class_id" INTEGER REFERENCES "class"("class_id") ON DELETE CASCADE,
    PRIMARY KEY ("user_id", "class_id")
);

-- Create type_exam table
CREATE TABLE IF NOT EXISTS "type_exam" (
    "type_exam_id" SERIAL PRIMARY KEY,
    "type_exam_name" VARCHAR(100) NOT NULL CHECK (LENGTH("type_exam_name") > 0)
);

-- Insert data to type_exam
INSERT INTO "type_exam" ("type_exam_name") VALUES ('20 Câu');
INSERT INTO "type_exam" ("type_exam_name") VALUES ('40 Câu');
INSERT INTO "type_exam" ("type_exam_name") VALUES ('60 Câu');
INSERT INTO "type_exam" ("type_exam_name") VALUES ('100 Câu');
INSERT INTO "type_exam" ("type_exam_name") VALUES ('120 Câu');

-- Create exam table
CREATE TABLE IF NOT EXISTS "exam" (
    "exam_id" SERIAL PRIMARY KEY,
    "class_id" INTEGER REFERENCES "class"("class_id") ON DELETE CASCADE,
    "exam_name" VARCHAR(255) NOT NULL CHECK (LENGTH("exam_name") > 0),
    "exam_date" TIMESTAMP NOT NULL,
    "point_exam" INTEGER NOT NULL CHECK ("point_exam" > 0),
    "num_exam" INTEGER NOT NULL CHECK ("num_exam" > 0),
    "type_exam_id" INTEGER NOT NULL REFERENCES "type_exam"("type_exam_id") ON DELETE CASCADE,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "user_exam" (
    "user_id" INTEGER REFERENCES "user"("user_id") ON DELETE CASCADE,
    "exam_id" INTEGER REFERENCES "exam"("exam_id") ON DELETE CASCADE,
    PRIMARY KEY ("user_id", "exam_id")
);

-- Create key table
CREATE TABLE IF NOT EXISTS "key" (
    "key_id" SERIAL PRIMARY KEY,
    "key_code" INTEGER NOT NULL CHECK ("key_code" > 0),
    "type_key" type_key,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create class_exam table
CREATE TABLE IF NOT EXISTS "exam_key" (
    "exam_id" INTEGER REFERENCES "exam"("exam_id") ON DELETE CASCADE,
    "key_id" INTEGER REFERENCES "key"("key_id") ON DELETE CASCADE,
    PRIMARY KEY ("key_id", "exam_id")
);

-- Create subject table
CREATE TABLE IF NOT EXISTS "subject" (
    "subject_id" SERIAL PRIMARY KEY,
    "subject_name" VARCHAR(50) NOT NULL CHECK (LENGTH("subject_name") > 0),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_subject table
CREATE TABLE IF NOT EXISTS "user_subject" (
    "user_id" INTEGER REFERENCES "user"("user_id") ON DELETE CASCADE,
    "subject_id" INTEGER REFERENCES "subject"("subject_id") ON DELETE CASCADE,
    PRIMARY KEY ("user_id", "subject_id")
);

-- Create clo table
CREATE TABLE IF NOT EXISTS "clo" (
    "clo_id" SERIAL PRIMARY KEY,
    "subject_id" INTEGER NOT NULL REFERENCES "subject"("subject_id") ON DELETE CASCADE,
    "clo_title" VARCHAR(50) NOT NULL CHECK (LENGTH("clo_title") > 0),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create question table
CREATE TABLE IF NOT EXISTS "question" (
    "question_id" SERIAL PRIMARY KEY,
    "clo_id" INTEGER NOT NULL REFERENCES "clo"("clo_id") ON DELETE CASCADE,
    "question_content" VARCHAR NOT NULL CHECK (LENGTH("question_content") > 0),
    "question_image" VARCHAR,
    "level" level NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create answer table
CREATE TABLE IF NOT EXISTS "answer" (
    "answer_id" SERIAL PRIMARY KEY,
    "question_id" INTEGER NOT NULL REFERENCES "question"("question_id") ON DELETE CASCADE,
    "answer_content" VARCHAR NOT NULL CHECK (LENGTH("answer_content") > 0),
    "is_correct" BOOLEAN NOT NULL DEFAULT FALSE,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create auto_question table
CREATE TABLE IF NOT EXISTS "auto_question" (
    "auto_question_id" SERIAL PRIMARY KEY,
    "question_id" INTEGER REFERENCES "question"("question_id") ON DELETE CASCADE,
    "key_id" INTEGER NOT NULL REFERENCES "key"("key_id") ON DELETE CASCADE,
    "question_index" INTEGER NOT NULL CHECK ("question_index" > 0),
    "correct_answer" VARCHAR(2),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Graded table
CREATE TABLE IF NOT EXISTS "graded" (
    "graded_id" SERIAL PRIMARY KEY,
    "user_code" INTEGER,
    "key_id" INTEGER REFERENCES "key"("key_id") ON DELETE CASCADE,
    "score" FLOAT NOT NULL CHECK ("score" >= 0),
    "image_key" INTEGER,
    "name_key" INTEGER,
    "graded_date" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Result table
CREATE TABLE IF NOT EXISTS "result" (
    "result_id" SERIAL PRIMARY KEY,
    "graded_id" INTEGER REFERENCES "graded"("graded_id") ON DELETE CASCADE,
    "question_index" INTEGER NOT NULL CHECK ("question_index" > 0),
    "choice" VARCHAR(2),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create lesson table
CREATE TABLE IF NOT EXISTS "lesson" (
    "lesson_id" SERIAL PRIMARY KEY,
    "class_id" INTEGER REFERENCES "class"("class_id") ON DELETE CASCADE,
    "room" VARCHAR(50) NOT NULL CHECK (LENGTH("room") > 0),
    "address" VARCHAR(255) NOT NULL CHECK (LENGTH("address") > 0),
    "date" TIMESTAMP NOT NULL,
    "start_time" TIMESTAMP NOT NULL,
    "end_time" TIMESTAMP NOT NULL,
    "present" INTEGER NOT NULL CHECK ("present" >= 0) DEFAULT 0,
    "late" INTEGER NOT NULL CHECK ("late" >= 0) DEFAULT 0,
    "absent" INTEGER NOT NULL CHECK ("absent" >= 0) DEFAULT 0,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create attendance table
CREATE TABLE IF NOT EXISTS "attendance" (
    "attendance_id" SERIAL PRIMARY KEY,
    "lesson_id" INTEGER REFERENCES "lesson"("lesson_id") ON DELETE CASCADE,
    "user_code" INTEGER,
    "status" status NOT NULL,
    "attended_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create face table
CREATE TABLE IF NOT EXISTS "face" (
    "face_id" SERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES "user"("user_id") ON DELETE CASCADE,
    "data_face" FLOAT[] NOT NULL,
    "face_key" INTEGER,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);