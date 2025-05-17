package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import java.util.*;

public class Admin extends User {

    private List<Customer> daftarCustomer;
    private List<Kamar> daftarKamar;
    private List<Reservasi> daftarReservasi;

    public Admin(String nama, String email, String password) {
        super(nama, email, password);
    }

    @Override
    public void info() {
        System.out.println("Admin: " + nama + " (" + email + ")");
    }

    public void tambahCustomer(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = SqlConnect.getConnection();
            String sql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, false)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getNama());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPassword());

            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public List<Customer> lihatCustomer() throws SQLException {
        List<Customer> daftarCustomer = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE isAdmin = 0";

        Connection conn = SqlConnect.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Customer cust = new Customer(
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("password")
            );
            cust.setIdUser(rs.getInt("id_user"));
            cust.setCreatedAt(rs.getTimestamp("createdAt"));
            daftarCustomer.add(cust);
        }

        return daftarCustomer;
    }

}
