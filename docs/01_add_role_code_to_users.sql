-- Run this migration on existing database if users.role_code does not exist.

ALTER TABLE users
ADD COLUMN IF NOT EXISTS role_code SMALLINT NOT NULL DEFAULT 1;

ALTER TABLE users
DROP CONSTRAINT IF EXISTS users_role_code_check;

ALTER TABLE users
ADD CONSTRAINT users_role_code_check CHECK (role_code IN (0, 1));

-- Promote one bootstrap account to ADMIN (0).
UPDATE users
SET
    role_code = 0,
    updated_at = NOW()
WHERE
    email = 'officenavi_user_001@example.com';