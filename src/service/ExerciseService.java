package service;

import dao.ExerciseDAO;
import model.Exercise;

public class ExerciseService {

    private ExerciseDAO exerciseDAO = new ExerciseDAO();

    public void addExercise(Exercise exercise) {

        if (exercise.getName() == null) {
            System.out.println("Exercise name is required!");
            return;
        }

        exerciseDAO.addExercise(exercise);
    }
}