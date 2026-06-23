CREATE DATABASE fitness_tracker;
USE fitness_tracker;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    age INT,
    height DECIMAL(5,2),
    weight DECIMAL(5,2),
    goal VARCHAR(50)
);
CREATE TABLE exercises (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    muscle_group VARCHAR(50),
    equipment VARCHAR(50),
    description TEXT
);
CREATE TABLE workouts (
    workout_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    date DATE,
    duration INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);
CREATE TABLE workout_exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    workout_id INT,
    exercise_id INT,
    sets INT,
    reps INT,
    weight DECIMAL(5,2),
    FOREIGN KEY (workout_id) REFERENCES workouts(workout_id)
        ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(exercise_id)
        ON DELETE CASCADE
);
CREATE TABLE body_progress (
    progress_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    date DATE,
    weight DECIMAL(5,2),
    body_fat DECIMAL(5,2),
    chest DECIMAL(5,2),
    waist DECIMAL(5,2),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);
CREATE TABLE personal_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    exercise_id INT,
    max_weight DECIMAL(5,2),
    max_reps INT,
    date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(exercise_id)
        ON DELETE CASCADE
);
