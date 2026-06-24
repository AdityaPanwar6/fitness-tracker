package dao;

import db.DBConnection;
import model.Workout;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WorkoutDAO {
    public void addWorkout(Workout workout){
        String query = "INSERT INTO workouts = (userId, date, duration) VALUES (?, ?, ?)";
        
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, workout.getUserId());
            stmt.setDate(2, workout.getDate());
            stmt.setInt(3, workout.getDuration());

            stmt.executeUpdate();

            System.out.println("Workout added " + workout);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
