package Jdbc.dao;

import Jdbc.database.DbConnection;
import Jdbc.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImplementation implements PersonDAO {

    @Override
    public int addPerson(Person person) {
        String query = "INSERT INTO Person(Name,Email) VALUES (?,?);";
        int lines = 0;
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query);) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            lines = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public int updatePerson(Person person) {
        String query = "UPDATE Person SET Name = ?, Email=? WHERE PersonID = ?;";
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setInt(3, person.getUserID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deletePerson(int id) {
        String query = "DELETE FROM Person WHERE PersonID = ?;";
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person getPerson(int id) {
        String query = "SELECT * FROM Person WHERE PersonID = ?;";
        try (Connection connection = DbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("PersonID");
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                return new Person(userId, name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Person> getAllPersons() {
        var users = new ArrayList<Person>();
        String query = "SELECT PersonID as ID , Name , Email FROM Person ORDER BY PersonID;";
        try (Connection connection = DbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                do {
                    int personId = resultSet.getInt("ID");
                    String name = resultSet.getString("Name");
                    String email = resultSet.getString("Email");
                    users.add(new Person(personId, name, email));
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar!");
            e.printStackTrace();
        }
        return users;
    }
}
