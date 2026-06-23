package model;

import java.sql.Date;

public class Workout{
    private int workoutId;
    private int userId;
    private Date date;
    private int duration;//in minutes

    //Constructor for existing workout
    public Workout(int userId, Date date, int duration){
        this.userId = userId;
        this.date = date;
        this.duration = duration;
    }
    //Constructor for new workout(id required)
    public Workout(int workoutId, int userId, Date date, int duration){
        this.workoutId = workoutId;
        this.userId = userId;
        this.date = date;
        this.duration = duration;
    }

    //Getters
    public int getWorkoutId(){return workoutId;}
    public int getUserId(){return userId;}
    public Date getDate(){return date;}
    public int getDuration(){return duration;}

    //Setter
    public void setWorkoutId(int workoutId){
        this.workoutId = workoutId;
    }
}
