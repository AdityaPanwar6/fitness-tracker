package dao;

import db.DBConnection;
import model.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO {

    //ADD NEW EXERCISE
    public void addExercise(Exercise exercise) {
        String query = "INSERT INTO exercises (name, muscle_group, equipment, description) " +
                       "VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getMuscleGroup());
            stmt.setString(3, exercise.getEquipment());
            stmt.setString(4, exercise.getDescription());

            stmt.executeUpdate();
            System.out.println("Exercise added: " + exercise.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //FETCH ALL EXERCISES(used to populate dropdown menus in the UI)
    public List<Exercise> getAllExercises() {
        List<Exercise> list = new ArrayList<>();
        String query = "SELECT * FROM exercises ORDER BY name";

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Exercise ex = new Exercise(
                    rs.getInt("exercise_id"),
                    rs.getString("name"),
                    rs.getString("muscle_group"),
                    rs.getString("equipment"),
                    rs.getString("description")
                );
                list.add(ex);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}