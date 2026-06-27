package service;

import dao.ExerciseDAO;
import model.Exercise;

import java.util.List;

public class ExerciseService {

    private ExerciseDAO exerciseDAO = new ExerciseDAO();

    //ADD EXERCISE
    public void addExercise(Exercise exercise) {
        if (exercise.getName() == null || exercise.getName().isBlank()) {
            System.out.println("Exercise name is required!");
            return;
        }
        exerciseDAO.addExercise(exercise);
    }

    //GET EX(for dropdown menus)
    public List<Exercise> getAllExercises() {
        return exerciseDAO.getAllExercises();
    }
}