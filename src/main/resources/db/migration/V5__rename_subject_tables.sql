ALTER TABLE subject
  RENAME TO "user";

ALTER TABLE "user"
  RENAME CONSTRAINT subject_pkey TO user_pkey;

ALTER SEQUENCE subject_id_seq
RENAME TO user_id_seq;

ALTER TABLE subject_role
  RENAME TO user_role;

ALTER SEQUENCE subject_role_id_seq
RENAME TO user_role_id_seq;

ALTER TABLE user_role
  RENAME COLUMN subject_id TO user_id;

ALTER TABLE user_role
  RENAME CONSTRAINT subject_role_pkey TO user_role_pkey;

ALTER TABLE user_role
  RENAME CONSTRAINT subject_role_role_id_fkey TO user_role_role_id_fkey;

ALTER TABLE user_role
  RENAME CONSTRAINT subject_role_subject_id_fkey TO user_role_user_id_fkey;