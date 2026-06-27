package model;

public class WorkoutExercise {
    private int id;
    private int workoutId;
    private int exerciseId;
    private int sets;
    private int reps;
    private double weight;

    public WorkoutExercise(){}

    //NEW ENTRY
    public WorkoutExercise(int workoutId, int exerciseId, int sets, int reps, double weight){
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
    //EXISTING ENTRY
    public WorkoutExercise(int id, int workoutId, int exerciseId, int sets, int reps, double weight){
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
    //GETTERS
    public int getId(){return id;}
    public int getWorkoutId(){return workoutId;}
    public int getExerciseId(){return exerciseId;}
    public int getSets(){return sets;}
    public int getReps(){return reps;}
    public double getWeight(){return weight;}

    //SETTERS
    public void setId(int id){
        this.id = id;
    }
    public void setWorkoutId(int workoutId){
        this.workoutId = workoutId;
    }
    public void setExerciseId(int exerciseId){
        this.exerciseId = exerciseId;
    }
    public void setSets(int sets){
        this.sets = sets;
    }
    public void setReps(int reps){
        this.reps = reps;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
}
