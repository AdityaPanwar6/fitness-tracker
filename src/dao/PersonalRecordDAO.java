package dao;

import db.DBConnection;
import model.PersonalRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalRecordDAO {

    //ADD NEW PR
    public void addRecord(PersonalRecord pr) {
        String query = "INSERT INTO personal_records (user_id, exercise_id, max_weight, max_reps, date) " + "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pr.getUserId());
            stmt.setInt(2, pr.getExerciseId());
            stmt.setDouble(3, pr.getMaxWeight());
            stmt.setInt(4, pr.getMaxReps());
            stmt.setDate(5, Date.valueOf(pr.getDate()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //GET ALL RECORDS FOR USER
    public List<PersonalRecord> getRecordsByUser(int userId) {
        List<PersonalRecord> list = new ArrayList<>();

        // JOIN so we can fetch exercise name
        String query =
            "SELECT pr.record_id, pr.user_id, pr.exercise_id, " +
            "       e.name AS exercise_name, " +
            "       pr.max_weight, pr.max_reps, pr.date " +
            "FROM personal_records pr " + "JOIN exercises e ON pr.exercise_id = e.exercise_id " + "WHERE pr.user_id = ? " + "ORDER BY pr.date DESC";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PersonalRecord pr = new PersonalRecord();
                pr.setRecordId(rs.getInt("record_id"));
                pr.setUserId(rs.getInt("user_id"));
                pr.setExerciseId(rs.getInt("exercise_id"));
                pr.setExerciseName(rs.getString("exercise_name"));
                pr.setMaxWeight(rs.getDouble("max_weight"));
                pr.setMaxReps(rs.getInt("max_reps"));
                pr.setDate(rs.getDate("date").toLocalDate());
                list.add(pr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //DELETE A PR
    public void deleteRecord(int recordId) {
        String query = "DELETE FROM personal_records WHERE record_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, recordId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}