package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

public abstract class User {

    protected int idUser;
    protected String nama;
    protected String email;
    protected String password;
    protected Timestamp createdAt;
    protected boolean isAdmin;

    // Constructor
    public User() {
    }

    public User(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public int getIdUser() {
        return idUser;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public boolean getIsAdmin() {
        return isAdmin;
    } 
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    // method
    public abstract void info(HttpServletRequest request);

    public static User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boolean isAdmin = rs.getBoolean("isAdmin");
                User user;

                if (isAdmin) {
                    user = new Admin(rs.getString("nama"), email, password);
                } else {
                    user = new Customer(rs.getString("nama"), email, password);
                }

                user.setIdUser(rs.getInt("id_user"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                return user;
            }
        }
        return null;
    }

    public static void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
}
