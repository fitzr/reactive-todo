CREATE DATABASE reactivetodo ENCODING 'UTF8';
USE reactivetodo;
CREATE TABLE IF NOT EXISTS todos(
    id serial PRIMARY KEY,
    user_id varchar(255) NOT NULL,
    title varchar(255) NOT NULL,
    content text NOT NULL,
    done boolean NOT NULL DEFAULT false
);
TRUNCATE TABLE todos;
