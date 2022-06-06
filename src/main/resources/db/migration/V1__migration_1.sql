CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(220) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    device_id VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(120) DEFAULT NULL,
    last_name VARCHAR(120) DEFAULT NULL
);

CREATE TABLE exercise
(
    id           BIGSERIAL PRIMARY KEY,
    muscle_group VARCHAR(100),
    exercise     VARCHAR(150)
);

CREATE TABLE training
(
    id BIGSERIAL PRIMARY KEY,
    start_training TIMESTAMP,
    end_training TIMESTAMP,
    status VARCHAR(20),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE exercise_training
(
    exercise_id BIGINT NOT NULL,
    training_id BIGINT NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise (id),
    FOREIGN KEY (training_id) REFERENCES training (id)
);

CREATE TABLE approach
(
    id BIGSERIAL PRIMARY KEY,
    repetitions BIGINT,
    weight BIGINT
);

CREATE TABLE exercise_approach
(
    exercise_id BIGINT NOT NULL,
    approach_id BIGINT NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise (id),
    FOREIGN KEY (approach_id) REFERENCES approach (id)
);
