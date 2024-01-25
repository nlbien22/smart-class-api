-- V3__cast_enum_to_varchar.sql

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'user_role'::regtype) THEN
    DROP CAST (character varying AS user_role);
  END IF;

  CREATE CAST (character varying AS user_role) WITH INOUT AS IMPLICIT;
END $$;

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'user_provider'::regtype) THEN
    DROP CAST (character varying AS user_provider);
  END IF;

  CREATE CAST (character varying AS user_provider) WITH INOUT AS IMPLICIT;
END $$;