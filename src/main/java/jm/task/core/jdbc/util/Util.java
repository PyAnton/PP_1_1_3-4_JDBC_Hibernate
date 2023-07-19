package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://192.168.0.15:3306/kata";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    private Util() {

    }

    public static Connection getConnection() {

        return connection;

    }

}
