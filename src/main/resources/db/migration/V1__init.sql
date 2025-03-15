CREATE TABLE `authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `is_active` tinyint DEFAULT NULL,
  `activation_token` varchar(255) DEFAULT NULL,
  `token_expiry_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UKlqjrcobrh9jc8wpcar64q1bfh` (`user_name`)
);

CREATE TABLE `user_authorities` (
  `authority_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`authority_id`,`user_id`),
  KEY `FKmj13d0mnuj4cd8b6htotbf9mm` (`user_id`),
  CONSTRAINT `FK2n9bab2v62l3y2jgu3qup4etw` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKmj13d0mnuj4cd8b6htotbf9mm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE Season (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number INT NOT NULL,
    release_date DATE,
    avg_rating FLOAT DEFAULT NULL
);

CREATE TABLE Episode (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number INT NOT NULL,
    season_id INT NOT NULL,
    release_date DATE,
    title VARCHAR(255) NOT NULL,
    imdb_rating FLOAT DEFAULT NULL,
    FOREIGN KEY (season_id) REFERENCES Season(id)
);

CREATE TABLE Question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    season_id INT,
    episode_id INT,
    quote TEXT NOT NULL,
    FOREIGN KEY (season_id) REFERENCES Season(id),
    FOREIGN KEY (episode_id) REFERENCES Episode(id)
);

CREATE TABLE Options (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT NOT NULL,
    text VARCHAR(255) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES Question(id)
);

CREATE TABLE Quiz (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    quiz_type SMALLINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP DEFAULT NULL,
    score INT DEFAULT 0,
    season_id INT DEFAULT NULL,
    character_name varchar(255),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (season_id) REFERENCES Season(id)
);

CREATE TABLE FunFact (
    id INT PRIMARY KEY AUTO_INCREMENT,
    related_to SMALLINT DEFAULT 0,
    season_id INT DEFAULT NULL,
    fact TEXT NOT NULL,
    FOREIGN KEY (season_id) REFERENCES Season(id)
);

CREATE VIEW Leaderboard AS
SELECT
    q.user_id AS id,
    u.user_name AS name,
    MAX(q.score) AS score
FROM
    Quiz q
JOIN
    User u ON q.user_id = u.id
GROUP BY
    q.user_id, u.user_name
ORDER BY
    score DESC;