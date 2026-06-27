package ui;

import model.User;

/**
 * Simple session holder.
 * After login succeeds, call Session.setCurrentUser(user).
 * Any panel can then call Session.getCurrentUser() to know who is logged in.
 */
public class Session {

    private static User currentUser = null;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}