package Jdbc.dao;

import Jdbc.model.Person;

import java.util.List;

public interface PersonDAO {
    public int addPerson(Person person);
    public int updatePerson(Person person);
    public int deletePerson(int id);
    public Person getPerson(int id);
    public List<Person> getAllPersons();
}
