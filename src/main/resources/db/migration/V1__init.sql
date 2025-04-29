CREATE TABLE authority (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) DEFAULT NULL
);

CREATE TABLE "users" (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  user_name VARCHAR(255) DEFAULT NULL,
  uuid VARCHAR(255) DEFAULT NULL,
  is_active BOOLEAN DEFAULT NULL,
  activation_token VARCHAR(255) DEFAULT NULL,
  token_expiry_time BIGINT DEFAULT NULL,
  CONSTRAINT UKob8kqyqqgmefl0aco34akdtpe UNIQUE (email),
  CONSTRAINT UKlqjrcobrh9jc8wpcar64q1bfh UNIQUE (user_name)
);

CREATE TABLE user_authorities (
  authority_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (authority_id, user_id),
  CONSTRAINT FKmj13d0mnuj4cd8b6htotbf9mm FOREIGN KEY (user_id) REFERENCES "user" (id),
  CONSTRAINT FK2n9bab2v62l3y2jgu3qup4etw FOREIGN KEY (authority_id) REFERENCES authority (id)
);

CREATE TABLE season (
  id SERIAL PRIMARY KEY,
  number INT NOT NULL,
  release_date DATE,
  avg_rating FLOAT DEFAULT NULL
);

CREATE TABLE episode (
  id SERIAL PRIMARY KEY,
  number INT NOT NULL,
  season_id INT NOT NULL,
  release_date DATE,
  title VARCHAR(255) NOT NULL,
  imdb_rating FLOAT DEFAULT NULL,
  FOREIGN KEY (season_id) REFERENCES season(id)
);

CREATE TABLE question (
  id SERIAL PRIMARY KEY,
  episode_id INT,
  quote TEXT NOT NULL,
  FOREIGN KEY (episode_id) REFERENCES episode(id)
);

CREATE TABLE options (
  id SERIAL PRIMARY KEY,
  question_id INT NOT NULL,
  text VARCHAR(255) NOT NULL,
  is_correct BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE quiz (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  quiz_type SMALLINT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  completed_at TIMESTAMP DEFAULT NULL,
  score INT DEFAULT 0,
  season_id INT DEFAULT NULL,
  character_name VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES "user"(id),
  FOREIGN KEY (season_id) REFERENCES season(id)
);

CREATE TABLE funfact (
  id SERIAL PRIMARY KEY,
  related_to SMALLINT DEFAULT 0,
  season_id INT DEFAULT NULL,
  fact TEXT NOT NULL,
  FOREIGN KEY (season_id) REFERENCES season(id)
);

CREATE VIEW leaderboard AS
SELECT
    q.user_id AS id,
    u.user_name AS name,
    MAX(q.score) AS score
FROM
    quiz q
JOIN
    "user" u ON q.user_id = u.id
GROUP BY
    q.user_id, u.user_name
ORDER BY
    score DESC;
