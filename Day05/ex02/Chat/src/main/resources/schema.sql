CREATE SCHEMA mjuli_chat;

SET search_path TO mjuli_chat;

CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY UNIQUE,
  login VARCHAR(80) NOT NULL UNIQUE,
  password VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS chatrooms (
    id BIGSERIAL PRIMARY KEY UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    owner_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY UNIQUE,
    author_id BIGINT REFERENCES users(id),
    room_id BIGINT REFERENCES chatrooms(id),
    text TEXT,
    time TIMESTAMP NOT NULL DEFAULT LOCALTIMESTAMP(0)
);

CREATE TABLE IF NOT EXISTS users_chatrooms (
    user_id BIGINT REFERENCES users(id),
    chatroom_id BIGINT REFERENCES chatrooms(id)
);