-- V1__create_user_role_type.sql

DO $$

BEGIN

IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
CREATE TYPE user_role AS ENUM ('ADMIN', 'TEACHER', 'STUDENT');
END IF;

IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_provider') THEN
    CREATE TYPE user_provider AS ENUM ('EMAIL', 'GOOGLE');
END IF;

END $$;