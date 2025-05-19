package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import java.util.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

public class Customer extends User {

    private Reservasi Reservasi;

    public Customer(String nama, String email, String password) {
        super(nama, email, password);
    }

    public Reservasi getReservasiList() {
        return Reservasi;
    }

    @Override
    public void info(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", getIdUser());
        session.setAttribute("userName", getNama());
        session.setAttribute("userEmail", getEmail());
        session.setAttribute("isAdmin", false);
        session.setAttribute("userCreatedAt", getCreatedAt());
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

    public boolean buatReservasi() throws SQLException {
        String sql = "INSERT INTO reservasi (id_user, id_kamar, tanggal_checkin, tanggal_checkout, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Reservasi.getIdUser());
            stmt.setInt(2, Reservasi.getIdKamar());
            stmt.setDate(3, Reservasi.getCheckIn());
            stmt.setDate(4, Reservasi.getCheckOut());

            return stmt.executeUpdate() > 0;
        }
    }

}
