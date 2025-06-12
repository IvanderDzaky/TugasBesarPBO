package hotel.config;

import java.sql.*;

public class SqlConnect {

    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12784330";
    private static final String USER = "sql12784330";
    private static final String PASSWORD = "siSKLFWpjm";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
