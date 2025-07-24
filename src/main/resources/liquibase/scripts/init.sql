-- liquibase formatted sql

-- changeset ros1nka:1
CREATE TABLE IF NOT EXISTS notification_task(
id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
chat_id BIGINT NOT NULL,
notification_text TEXT NOT NULL,
datetime TIMESTAMP NOT NULL,
is_sent BOOLEAN NOT NULL DEFAULT FALSE
)