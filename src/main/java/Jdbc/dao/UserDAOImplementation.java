package Jdbc.dao;

import Jdbc.database.DbConnection;
import Jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImplementation implements UserDAO {

    @Override
    public int addUser(User user) {
        String query = "INSERT INTO User(Name,Email) VALUES (?,?);";
        int lines;
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query);) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            lines = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public int updateUser(User user) {
        String query = "UPDATE User SET Name = ?, Email=? WHERE UserID = ?;";
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getUserID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteUser(int id) {
        String query = "DELETE FROM User WHERE UserID = ?;";
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int id) {
        String query = "SELECT * FROM User WHERE UserID = ?;";
        try (Connection connection = DbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("UserID");
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                return new User(userId, name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<User> getAllUsers() {
        var users = new ArrayList<User>();
        String query = "SELECT UserId as ID , Name , Email FROM User ORDER BY UserID;";
        try (Connection connection = DbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                do {
                    int userId = resultSet.getInt("ID");
                    String name = resultSet.getString("Name");
                    String email = resultSet.getString("Email");
                    users.add(new User(userId, name, email));
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar!");
            e.printStackTrace();
        }
        return users;
    }
}
