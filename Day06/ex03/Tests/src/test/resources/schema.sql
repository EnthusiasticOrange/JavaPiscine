CREATE TABLE products (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
  name VARCHAR (255) NOT NULL,
  price INTEGER NOT NULL CHECK (price > 0)
);