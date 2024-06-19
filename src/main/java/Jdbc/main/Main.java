package Jdbc.main;

import Jdbc.dao.PersonDAOImplementation;
import Jdbc.model.Person;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonDAOImplementation personDAO = new PersonDAOImplementation();
        List<Person> persons;
        //Add
        Person person = new Person();
        person.setName("John Travolta");
        person.setEmail("mail@mail.com");
        personDAO.addPerson(person);
        //Update
        person = new Person(1,"Updated John Travolta","travolta@mail.com");
        personDAO.updatePerson(person);
        //Delete
        System.out.println("Linhas deletadas: "+personDAO.deletePerson(4));
        //Get All Users
        persons = personDAO.getAllPersons();
        System.out.println(persons);
        //Get 1 user
        System.out.println(personDAO.getPerson(1));

    }
}