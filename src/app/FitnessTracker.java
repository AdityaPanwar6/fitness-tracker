package app;

//import dao.ExerciseDAO;
//import dao.UserDAO;
import db.DBConnection;
//import model.*;

public class FitnessTracker{
    public static void main(String args[]){
        System.out.println("Welcome to Fitness Tracker app.");
        DBConnection.getConnection();
        
        //SAMPLE INPUT
        /* 
        User toji = new User("Toji","zenin@gmail.com","toji",35,5.9,75.0,"Maintainance");
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(toji);

        Exercise pullUp = new Exercise("Pull Up","Sholder", "Bar", "Compound movement exercise");
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        exerciseDAO.addExercise(pullUp);

        */

       
    }
}