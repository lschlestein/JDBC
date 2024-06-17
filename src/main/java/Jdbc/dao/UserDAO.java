package Jdbc.dao;

import Jdbc.model.User;

import java.util.List;

public interface UserDAO {
    public int addUser(User user);
    public int updateUser(User user);
    public int deleteUser(int id);
    public User getUser(int id);
    public List<User> getAllUsers();
}
