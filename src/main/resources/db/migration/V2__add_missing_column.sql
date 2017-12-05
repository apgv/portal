ALTER TABLE membership_type
  ADD COLUMN original_id INTEGER NOT NULL DEFAULT -1;

UPDATE membership_type
SET original_id = id;

ALTER TABLE membership_type
  ALTER COLUMN original_id DROP DEFAULT;