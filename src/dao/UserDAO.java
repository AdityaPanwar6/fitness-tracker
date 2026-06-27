package dao;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    //REGISTER NEW USER
    public void addUser(User user) {
        String query = "INSERT INTO users (name, email, password, age, height, weight, goal) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            System.out.println("User registered: " + user.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //LOGIN
    public User login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAge(rs.getInt("age"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setGoal(rs.getString("goal"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//CREDENTIALS NOT MATCHED(LOGIN FAILED)
    }

    //UPDATE USER
    public void updateUser(User user) {
    String query = "UPDATE users SET name = ?, age = ?, height = ?, weight = ?, goal = ? WHERE user_id = ?";
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setString(1, user.getName());
        stmt.setInt(2, user.getAge());
        stmt.setDouble(3, user.getHeight());
        stmt.setDouble(4, user.getWeight());
        stmt.setString(5, user.getGoal());
        stmt.setInt(6, user.getId());
        
        stmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}