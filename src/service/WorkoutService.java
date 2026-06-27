package service;

import dao.WorkoutDAO;
import dao.WorkoutExerciseDAO;
import model.Workout;
import model.WorkoutExercise;

import java.util.List;

public class WorkoutService {

    private WorkoutDAO workoutDAO = new WorkoutDAO();
    private WorkoutExerciseDAO workoutExerciseDAO = new WorkoutExerciseDAO();

    //CREATE WORKOUT
    public int createWorkout(Workout workout) {

        if (workout.getUserId() <= 0) {
            System.out.println("Invalid user ID!");
            return -1;
        }

        return workoutDAO.addWorkout(workout);
    }

    //ADD EXS TO WORKOUT
    public void addExercisesToWorkout(int workoutId, List<WorkoutExercise> exercises) {

        if (workoutId <= 0) {
            System.out.println("Invalid workout ID!");
            return;
        }

        for (WorkoutExercise we : exercises) {
            we.setWorkoutId(workoutId);
            workoutExerciseDAO.addExerciseToWorkout(we);
        }

        System.out.println("Exercises added to workout!");
    }

    //GET WORKOUT BY USER
    public List<Workout> getUserWorkouts(int userId) {
        return workoutDAO.getWorkoutsByUser(userId);
    }

    //GET ALL WORKOUTS
    public List<Workout> getAllWorkouts() {
        return workoutDAO.getAllWorkouts();
    }

    //GET WORKOUT STREAK
    public int getUserStreak(int userId) {
    return workoutDAO.calculateCurrentStreak(userId);
}
}