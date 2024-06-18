package Jdbc.model;

public class Person {
    private int personID;
    private String name;
    private String email;

    public Person(int personID, String nome, String email) {
        this.personID = personID;
        this.name = nome;
        this.email = email;
    }

    public int getUserID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Person() {
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}'+"\n";
    }
}
