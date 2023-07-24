package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String sql;
    private static final Connection connection = Util.getConnection();

    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users (" +
                "    id INT PRIMARY KEY AUTO_INCREMENT," +
                "    name VARCHAR(50) NOT NULL," +
                "    lastName VARCHAR(50) NOT NULL," +
                "    age INT(3) NOT NULL" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }


    }

    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO users (name, lastName, age)\n" +
                "VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        sql = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        sql = "SELECT * FROM users;";
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        sql = "DELETE FROM users;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
