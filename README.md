#  Fitness Tracker (Java + Swing + JDBC)

A full-featured **Fitness Tracker Desktop Application** built using **Java, Swing (GUI), JDBC, and MySQL**, following a clean **layered architecture (Model–DAO–Service–UI)**.

This application allows users to track workouts, manage exercises, monitor body progress, and record personal fitness achievements — all through an intuitive graphical interface.

---

##  Features

### > User Management

* User registration and profile management
* Secure login validation (via service layer)

### > Workout Tracking

* Create and manage workouts
* Add multiple exercises to a workout
* Track duration and workout history

### > Exercise Management

* Preloaded exercise database (structured & consistent)
* Categorized by muscle group and equipment

### > Progress Tracking

* Record body metrics (weight, measurements)
* Monitor fitness progress over time

### > Personal Records

* Track best performance for exercises (PRs)
* View achievements and milestones

### > GUI Interface (Swing)

* Fully interactive desktop UI
* Organized navigation and forms
* User-friendly design (non-console based)

---

##  Architecture

The project follows a **layered architecture**:

```
UI (Swing)
   ↓
Service Layer (Business Logic)
   ↓
DAO Layer (Database Operations)
   ↓
MySQL Database
```

---

##  Technologies Used

* **Java (Core Java)**
* **Swing (GUI Development)**
* **JDBC (Database Connectivity)**
* **MySQL**
* **OOP & Layered Architecture**

---

##  Setup Instructions

### 1 > Clone the Repository

```
git clone https://github.com/AdityaPanwar6/fitness-tracker.git
cd fitness-tracker
```

---

### 2 > Setup Database

* Open MySQL
* Run the schema file:

```
database/schema.sql
```

---

### 3 >  Configure Database Connection

Since credentials are not included for security reasons:

1. Copy the src/db/DBConnection.java file:

2. Update your credentials:

```java
private static final String USER = "your username";
private static final String PASSWORD = "yourpassword";
```

---

### 4 >  Add MySQL Connector

Download MySQL Connector/J:

--> https://dev.mysql.com/downloads/connector/j/

Place the `.jar` file inside:

```
lib/
```

---

### 5 >  Run the Application

Run:

```
src/app/FitnessTracker.java
```

---

##  Sample Data

It is recommended to pre-load basic exercises into the database for better usability.

Here, I have provided query to add basic exercises to you database.(Highly recommend you to add more by yourself.)
-- Ensure your target database is selected
-- USE fitness_tracker

INSERT INTO exercises (name, muscle_group, equipment, description) VALUES
('Bench Press', 'Chest', 'Barbell', 'Lie flat on a bench and press the barbell upwards to build pectoral and tricep strength.'),
('Squats', 'Legs', 'Barbell', 'Place barbell on upper back, drop hips lower than knees, and drive upward to target quadriceps and glutes.'),
('Deadlift', 'Back', 'Barbell', 'Lift a loaded barbell off the ground to hip level keeping a flat back, engaging the entire posterior chain.'),
('Overhead Press', 'Shoulders', 'Barbell', 'Press the barbell directly overhead from shoulder level while standing to develop deltoids.'),
('Bicep Curls', 'Arms', 'Dumbbell', 'Isolate and curl dumbbells upwards towards the shoulders to develop the biceps brachii muscles.'),
('Lat Pulldown', 'Back', 'Cable Machine', 'Pull the overhead bar attachment down vertically to upper chest level to expand back width and lat muscles.');

---

## >  Future Improvements

* Authentication & password hashing
* Export workout reports (PDF/CSV)
* Charts for progress visualization
* REST API version (Spring Boot)
* Mobile app integration

---

##  Author

**Aditya Panwar**

* GitHub: https://github.com/AdityaPanwar6

---

##  Contributing

Contributions are welcome! Feel free to fork the repo and submit a pull request.

---

##  License

This project is open-source and available under the MIT License.
