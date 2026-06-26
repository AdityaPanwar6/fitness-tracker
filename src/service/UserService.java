package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    // Register User
    public void registerUser(User user) {

        if (user.getName() == null || user.getEmail() == null) {
            System.out.println("Invalid user data!");
            return;
        }

        userDAO.addUser(user);
    }
}