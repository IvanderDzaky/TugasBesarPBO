package hotel.config;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SqlConnect {
    private static Connection conn;
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/HotelReservation";
                String user = "root";
                String pass = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                conn = (Connection) DriverManager.getConnection(url, user, pass);
            } catch (Exception err) {
                Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE,null,err);
            }
        }
        return conn;
    }
}
