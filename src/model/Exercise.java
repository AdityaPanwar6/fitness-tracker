package model;

public class Exercise {
    private int exerciseId;
    private String name;
    private String muscleGroup;
    private String equipment;
    private String description;

    //Constructor for adding new exerises

    public Exercise(String name, String muscleGroup, String equipment, String description){
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.equipment = equipment;
        this.description = description;
    }
    //Constructor to Fetch exercises

    public Exercise(int exerciseId, String name, String muscleGroup, String equipment, String description){
        this.exerciseId = exerciseId;
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.equipment = equipment;
        this.description = description;
    }

    //Getters
    public int getExerciseId(){return exerciseId;}
    public String getName(){return name;}
    public String getMuscleGroup(){return muscleGroup;}
    public String getEquipment(){return equipment;}
    public String getDescription(){return description;}

    //Setter
    public void setExerciseId(int exerciseId){
        this.exerciseId = exerciseId;
    }
}
