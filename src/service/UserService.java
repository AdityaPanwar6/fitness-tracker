package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    //REGISTER NEW USER
    public void registerUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            System.out.println("Name is required!");
            return;
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            System.out.println("Email is required!");
            return;
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            System.out.println("Password is required!");
            return;
        }
        userDAO.addUser(user);
    }

    //LOGIN returns the User if credentials are valid, null otherwise
    public User login(String email, String password) {
        if (email == null || password == null) return null;
        return userDAO.login(email, password);
    }

    //UPDATE USER PROFILE
    public void updateUserProfile(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
        System.out.println("Profile name cannot be blank!");
        return;
    }
    userDAO.updateUser(user);
}
}