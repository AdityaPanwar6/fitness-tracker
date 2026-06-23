package dao;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    public void addUser(User user) {
    String query = "INSERT INTO users (name, email, password, age, height, weight, goal) VALUES (?,?,?,?,?,?,?)";

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());
        stmt.setInt(4, user.getAge());
        stmt.setDouble(5, user.getHeight());
        stmt.setDouble(6, user.getWeight());
        stmt.setString(7, user.getGoal());

        stmt.executeUpdate();

        System.out.println("User added successfully!");

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
