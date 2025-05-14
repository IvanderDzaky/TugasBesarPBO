package hotel.model;

import hotel.config.SqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer extends User {

    public Customer(String nama, String email, String password) {
        super(nama, email, password);
    }

    @Override
    public void info() {
        System.out.println("Customer: " + nama + " (" + email + ")");
    }

    public boolean register() throws SQLException {
        String sql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, false)";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, password);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }
}