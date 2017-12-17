ALTER TABLE membership
  RENAME COLUMN deleted_by TO changed_by;

ALTER TABLE membership
  RENAME COLUMN deleted_date TO changed_date;

ALTER TABLE membership_type
  RENAME COLUMN deleted_by TO changed_by;

ALTER TABLE membership_type
  RENAME COLUMN deleted_date TO changed_date;

ALTER TABLE person
  RENAME COLUMN deleted_by TO changed_by;

ALTER TABLE person
  RENAME COLUMN deleted_date TO changed_date;

ALTER TABLE "user"
  RENAME COLUMN deleted_by TO changed_by;

ALTER TABLE "user"
  RENAME COLUMN deleted_date TO changed_date;

ALTER TABLE user_role
  RENAME COLUMN deleted_by TO changed_by;

ALTER TABLE user_role
  RENAME COLUMN deleted_date TO changed_date;
