-- Run this file while connected to a maintenance DB such as "postgres".
-- It recreates the app database from scratch.

SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE
    datname = 'office-navi'
    AND pid <> pg_backend_pid();

DROP DATABASE IF EXISTS "office-navi";

CREATE DATABASE "office-navi";