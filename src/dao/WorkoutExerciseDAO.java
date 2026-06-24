package dao;

import db.DBConnection;
import model.WorkoutExercise;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WorkoutExerciseDAO {
        public void addWorkoutExercise(WorkoutExercise workoutExercise){
            String query = "INSERT INTO workout_exercises (workoutId, exerciseId, sets, reps, weight) VALUES (?, ?, ?, ?, ?)";

        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1,workoutExercise.getWorkoutId());
            stmt.setInt(2, workoutExercise.getExerciseId());
            stmt.setInt(3,workoutExercise.getSets());
            stmt.setInt(4,workoutExercise.getReps());
            stmt.setDouble(5, workoutExercise.getWeight());

            stmt.executeUpdate();

            System.out.println("WorkoutExercise.java exercuted.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
