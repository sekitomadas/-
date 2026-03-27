-- Run this migration on database: "office-navi".
-- Purpose:
-- 1) Create office location master table.
-- 2) Add FK-based location_id to seats.
-- 3) Backfill location_id from existing seats.location values.
--
-- Note:
-- The old seats.location column is intentionally kept for backward compatibility.

BEGIN;

CREATE TABLE IF NOT EXISTS office_locations (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Fallback location for rows where seats.location is NULL/blank.
INSERT INTO office_locations (code, name)
VALUES ('UNKNOWN', '不明')
ON CONFLICT (code) DO NOTHING;

-- Add new FK column (nullable during migration).
ALTER TABLE seats
ADD COLUMN IF NOT EXISTS location_id BIGINT;

-- Seed master data from existing seat locations.
INSERT INTO office_locations (code, name)
SELECT DISTINCT
    UPPER(REPLACE(TRIM(location), ' ', '_')) AS code,
    TRIM(location) AS name
FROM seats
WHERE location IS NOT NULL
  AND TRIM(location) <> ''
ON CONFLICT (name) DO NOTHING;

-- Backfill location_id from seats.location.
UPDATE seats s
SET location_id = ol.id
FROM office_locations ol
WHERE s.location IS NOT NULL
  AND TRIM(s.location) <> ''
  AND TRIM(s.location) = ol.name
  AND s.location_id IS NULL;

-- Assign UNKNOWN to rows that still have no mapped location.
UPDATE seats s
SET location_id = ol.id
FROM office_locations ol
WHERE s.location_id IS NULL
  AND ol.code = 'UNKNOWN';

-- FK + index.
ALTER TABLE seats
DROP CONSTRAINT IF EXISTS fk_seats_location;

ALTER TABLE seats
ADD CONSTRAINT fk_seats_location
FOREIGN KEY (location_id) REFERENCES office_locations (id);

CREATE INDEX IF NOT EXISTS idx_seats_location_id ON seats (location_id);

COMMIT;
