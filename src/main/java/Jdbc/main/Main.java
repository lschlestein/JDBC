package Jdbc.main;

import Jdbc.dao.UserDAOImplementation;
import Jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAOImplementation userDAO = new UserDAOImplementation();
        List<User> users;
        //Add
        User user = new User();
        user.setName("John Travolta");
        user.setEmail("mail@mail.com");
        userDAO.addUser(user);
        //Update
        user = new User(1,"Updated John Travolta","travolta@mail.com");
        userDAO.updateUser(user);
        //Delete
        System.out.println("Linhas deletadas: "+userDAO.deleteUser(2));
        //Get All Users
        users = userDAO.getAllUsers();
        System.out.println(users);
        //Get 1 user
        System.out.println(userDAO.getUser(3));

    }
}