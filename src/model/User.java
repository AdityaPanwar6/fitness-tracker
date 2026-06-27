package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int age;
    private double height;
    private double weight;
    private String goal;

    public User() {}

    //NEW USER (no id yet)
    public User(String name, String email, String password, int age, double height, double weight, String goal) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
    }

    //EXISTING USER(id known)
    public User(int id, String name, String email, String password, int age, double height, double weight, String goal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
    }

    //GETTERS
    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getPassword(){ return password; }
    public int getAge(){ return age; }
    public double getHeight(){ return height; }
    public double getWeight(){ return weight; }
    public String getGoal(){ return goal; }

    //SETTERS
    public void setId(int id){ this.id = id; }
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
    public void setPassword(String pass){ this.password = pass; }
    public void setAge(int age){ this.age = age; }
    public void setHeight(double height){ this.height = height; }
    public void setWeight(double weight){ this.weight = weight; }
    public void setGoal(String goal){ this.goal = goal; }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}