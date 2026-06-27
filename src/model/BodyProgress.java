package model;

import java.time.LocalDate;

public class BodyProgress {
    private int progressId;
    private int userId;
    private LocalDate date;
    private double weight;
    private double bodyFat;
    private double chest;
    private double waist;

    //CONSTRUCTORS
    public BodyProgress(){}

    public BodyProgress(int userId, LocalDate date, double weight, double bodyFat, double chest, double waist){
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.chest = chest;
        this.waist = waist;
    }

    //GETTERS
    public int getProgressId(){return progressId;}
    public int getUserId(){return userId;}
    public LocalDate getDate(){return date;}
    public double getWeight(){return weight;}
    public double getBodyFat(){return bodyFat;}
    public double getChest(){return chest;}
    public double getWaist(){return waist;}

    //SETTERS
    public void setProgressId(int progressId){this.progressId = progressId;}
    public void setUserId(int userId){this.userId = userId;}
    public void setDate(LocalDate date){this.date = date;}
    public void setWeight(double weight){this.weight = weight;}
    public void setBodyFat(double bodyFat){this.bodyFat = bodyFat;}
    public void setChest(double chest){this.chest = chest;}
    public void setWaist(double waist){this.waist = waist;}
}
