package model;

import java.time.LocalDate;

public class Workout{
    private int workoutId;
    private int userId;
    private LocalDate date;
    private int duration;//in minutes

    public Workout(){}

    //Constructor for existing workout
    public Workout(int userId, LocalDate date, int duration){
        this.userId = userId;
        this.date = date;
        this.duration = duration;
    }
    //Constructor for new workout(id required)
    public Workout(int workoutId, int userId, LocalDate date, int duration){
        this.workoutId = workoutId;
        this.userId = userId;
        this.date = date;
        this.duration = duration;
    }

    //Getters
    public int getWorkoutId(){return workoutId;}
    public int getUserId(){return userId;}
    public LocalDate getDate(){return date;}
    public int getDuration(){return duration;}

    //Setters
    public void setWorkoutId(int workoutId){
        this.workoutId = workoutId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
}
