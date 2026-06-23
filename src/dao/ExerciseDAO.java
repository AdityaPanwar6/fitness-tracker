package dao;

import db.DBConnection;
import model.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExerciseDAO {

    public void addExercise(Exercise exercise) {
        String query = "INSERT INTO exercises (name, muscle_group, equipment, description) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getMuscleGroup());
            stmt.setString(3, exercise.getEquipment());
            stmt.setString(4, exercise.getDescription());

            stmt.executeUpdate();
            System.out.println("Exercise added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}