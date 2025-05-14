package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;

public abstract class User {
    protected int id;
    protected String nama;
    protected String email;
    protected String password;

    public User() {}

    public User(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public abstract void info();

    // Login static: mengembalikan Admin / Customer sesuai data
    public static User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = SqlConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

                user.setId(rs.getInt("id"));
                return user;
            }
        }
        return null;
    }
}
