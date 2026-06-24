package dao;

import db.DBConnection;
import model.WorkoutExercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutExerciseDAO {

    //ADD EXERCISE TO WORKOUT
    public void addExerciseToWorkout(WorkoutExercise we) {
        String query = "INSERT INTO workout_exercises (workout_id, exercise_id, sets, reps, weight) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, we.getWorkoutId());
            stmt.setInt(2, we.getExerciseId());
            stmt.setInt(3, we.getSets());
            stmt.setInt(4, we.getReps());
            stmt.setDouble(5, we.getWeight());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //GET ALL EXERCISES FOR A WORKOUT
    public List<WorkoutExercise> getExercisesByWorkout(int workoutId) {
        List<WorkoutExercise> list = new ArrayList<>();
        String query = "SELECT * FROM workout_exercises WHERE workout_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, workoutId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                WorkoutExercise we = new WorkoutExercise();
                we.setId(rs.getInt("id"));
                we.setWorkoutId(rs.getInt("workout_id"));
                we.setExerciseId(rs.getInt("exercise_id"));
                we.setSets(rs.getInt("sets"));
                we.setReps(rs.getInt("reps"));
                we.setWeight(rs.getDouble("weight"));

                list.add(we);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //DELETE EXERCISES BY WORKOUT
    public void deleteByWorkout(int workoutId) {
        String query = "DELETE FROM workout_exercises WHERE workout_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, workoutId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}