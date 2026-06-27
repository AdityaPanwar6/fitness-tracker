package model;

import java.time.LocalDate;

public class PersonalRecord {
    private int recordId;
    private int userId;
    private int exerciseId;
    private String exerciseName;
    private double maxWeight;
    private int maxReps;
    private LocalDate date;

    public PersonalRecord(){}

    public PersonalRecord(int userId, int exerciseId, double maxWeight, int maxReps, LocalDate date) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.maxWeight = maxWeight;
        this.maxReps = maxReps;
        this.date = date;
    }

    //GETTERS
    public int getRecordId(){ return recordId; }
    public int getUserId(){ return userId; }
    public int getExerciseId(){ return exerciseId; }
    public String getExerciseName(){ return exerciseName; }
    public double getMaxWeight(){ return maxWeight; }
    public int getMaxReps(){ return maxReps; }
    public LocalDate getDate(){ return date; }

    //SETTERS
    public void setRecordId(int recordId){ this.recordId = recordId; }
    public void setUserId(int userId){ this.userId = userId; }
    public void setExerciseId(int exerciseId){ this.exerciseId = exerciseId; }
    public void setExerciseName(String name){ this.exerciseName = name; }
    public void setMaxWeight(double maxWeight){ this.maxWeight = maxWeight; }
    public void setMaxReps(int maxReps){ this.maxReps = maxReps; }
    public void setDate(LocalDate date){ this.date = date; }
}