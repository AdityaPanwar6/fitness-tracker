package model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private int age;
    private double height;
    private double weight;
    private String goal;

    //Constructor for existing user
    public User(String name, String email, String password, int age, double height, double weight, String goal){
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
    }

    //Constructor for existing user
    public User(int userId, String name, String email, String password, int age, double height, double weight, String goal){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
    }

    //getters
    public int getUserID(){return userId;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public int getAge(){return age;}
    public double getHeight(){return height;}
    public double getWeight(){return weight;}
    public String getGoal(){return goal;}

    public void setUserID(int userId){
        this.userId = userId;
    }
}