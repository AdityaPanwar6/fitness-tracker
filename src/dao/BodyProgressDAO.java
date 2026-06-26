package dao;

import db.DBConnection;
import model.BodyProgress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BodyProgressDAO {

    public void addProgress(BodyProgress bp) {
        String query = "INSERT INTO body_progress (user_id, date, weight, body_fat, chest, waist) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, bp.getUserId());
            stmt.setDate(2, Date.valueOf(bp.getDate()));
            stmt.setDouble(3, bp.getWeight());
            stmt.setDouble(4, bp.getBodyFat());
            stmt.setDouble(5, bp.getChest());
            stmt.setDouble(6, bp.getWaist());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BodyProgress> getProgressByUser(int userId) {
        List<BodyProgress> list = new ArrayList<>();
        String query = "SELECT * FROM body_progress WHERE user_id = ? ORDER BY date DESC";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BodyProgress bp = new BodyProgress();

                bp.setProgressId(rs.getInt("progress_id"));
                bp.setUserId(rs.getInt("user_id"));
                bp.setDate(rs.getDate("date").toLocalDate());
                bp.setWeight(rs.getDouble("weight"));
                bp.setBodyFat(rs.getDouble("body_fat"));
                bp.setChest(rs.getDouble("chest"));
                bp.setWaist(rs.getDouble("waist"));

                list.add(bp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteProgress(int progressId) {
        String query = "DELETE FROM body_progress WHERE progress_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, progressId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
