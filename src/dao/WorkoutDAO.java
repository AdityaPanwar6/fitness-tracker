package dao;

import db.DBConnection;
import model.Workout;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {

    //CREATE WORKOUT
    public int addWorkout(Workout workout) {
        String query = "INSERT INTO workouts (user_id, date, duration) VALUES (?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, workout.getUserId());
            stmt.setDate(2, Date.valueOf(workout.getDate()));
            stmt.setInt(3, workout.getDuration());

            stmt.executeUpdate();

            // Get generated workout_id
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    //READ ALL WORKOUTS
    public List<Workout> getAllWorkouts() {
        List<Workout> list = new ArrayList<>();
        String query = "SELECT * FROM workouts";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Workout workout = new Workout();
                workout.setWorkoutId(rs.getInt("workout_id"));
                workout.setUserId(rs.getInt("user_id"));
                workout.setDate(rs.getDate("date").toLocalDate());
                workout.setDuration(rs.getInt("duration"));

                list.add(workout);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //READ WORKOUTS BY USER
    public List<Workout> getWorkoutsByUser(int userId) {
        List<Workout> list = new ArrayList<>();
        String query = "SELECT * FROM workouts WHERE user_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Workout workout = new Workout();
                workout.setWorkoutId(rs.getInt("workout_id"));
                workout.setUserId(rs.getInt("user_id"));
                workout.setDate(rs.getDate("date").toLocalDate());
                workout.setDuration(rs.getInt("duration"));

                list.add(workout);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}