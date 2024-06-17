package Jdbc.model;

public class User {
    private int userID;
    private String name;
    private String email;

    public User(int userID, String nome, String email) {
        this.userID = userID;
        this.name = nome;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User() {
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}'+"\n";
    }
}
