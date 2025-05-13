package hotel.model;

import hotel.config.SqlConnect;
import jakarta.servlet.http.HttpSession;
import java.sql.*;


public class User {

    private int id;
    private String nama;
    private String email;
    private String password;
    private boolean isAdmin;

    // Constructors
    public User() {
    }

    public User(String nama, String email, String password, boolean isAdmin) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean register() throws SQLException {
        String sql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setBoolean(4, isAdmin);
            return stmt.executeUpdate() > 0;
        }
    }

    public static User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNama(rs.getString("nama"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
                return user;
            }
        }
        return null;
    }


    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

}
