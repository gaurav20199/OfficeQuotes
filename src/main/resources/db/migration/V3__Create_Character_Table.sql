CREATE TABLE office_character (
    id SERIAL PRIMARY KEY,
    real_name VARCHAR(255) NOT NULL,
    character_name VARCHAR(255) NOT NULL,
    number_of_episodes SMALLINT,
    year_range VARCHAR(255) NOT NULL
);
