package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import java.util.*;

public class Customer extends User {

    private List<Reservasi> reservasiList = new ArrayList<>();

    public Customer(String nama, String email, String password) {
        super(nama, email, password);
    }

    public List<Reservasi> getReservasiList() {
        return reservasiList;
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

            return stmt.executeUpdate() > 0;
        }
    }
}
