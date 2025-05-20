package hotel.model;

import hotel.helper.Fasilitas;
import hotel.config.SqlConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

public class Admin extends User {

    private List<Customer> daftarCustomer;
    private List<Kamar> daftarKamar;
    private List<Reservasi> daftarReservasi;
    private List<Fasilitas> daftarFasilitas;

    public Admin(String nama, String email, String password) {
        super(nama, email, password);
    }

    @Override
    public void info(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", getIdUser());
        session.setAttribute("userName", getNama());
        session.setAttribute("userEmail", getEmail());
        session.setAttribute("isAdmin", this instanceof Admin);
        session.setAttribute("userCreatedAt", getCreatedAt());
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

    public void hapusCustomer(int idUser) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SqlConnect.getConnection();
            String sql = "DELETE FROM users WHERE id_user= ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idUser);
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

    public static void updateCustomer(int idUser, Customer customerBaru) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SqlConnect.getConnection();
            String sql = "UPDATE users SET nama = ?, email = ?, password = ? WHERE id_user = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerBaru.getNama());
            stmt.setString(2, customerBaru.getEmail());
            stmt.setString(3, customerBaru.getPassword());
            stmt.setInt(4, idUser);
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

    public List<Kamar> lihatKamar() throws SQLException {
        List<Kamar> daftarKamar = new ArrayList<>();
        Connection conn = SqlConnect.getConnection();
        String query = "SELECT * FROM Kamar ORDER BY nomor_kamar";

        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Kamar k = new Kamar(
                        rs.getString("nomor_kamar"),
                        rs.getString("tipe"),
                        rs.getDouble("harga"),
                        rs.getBoolean("status"),
                        rs.getInt("max_guest")
                );
                k.setIdKamar(rs.getInt("id_kamar"));
                k.setFasilitasList(k.lihatFasilitasKamar());
                daftarKamar.add(k);
            }
        }

        return daftarKamar;
    }

    // Tambah Kamar
    public boolean tambahKamar(Kamar kamar) throws SQLException {
        return kamar.saveToDB();
    }

    // Update Kamar
    public boolean updateKamar(Kamar kamar) throws SQLException {
        return kamar.updateToDB();
    }

    // Hapus Kamar
    public boolean hapusKamar(int idKamar) throws SQLException {
        return Kamar.deleteFromDB(idKamar);
    }

    public List<Fasilitas> lihatFasilitas() throws SQLException {
        List<Fasilitas> daftarFasilitas = new ArrayList<>();
        Connection conn = SqlConnect.getConnection();
        String sql = "SELECT * FROM fasilitas";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fasilitas fasilitas = new Fasilitas(
                        rs.getInt("id_fasilitas"),
                        rs.getString("nama_fasilitas")
                );
                daftarFasilitas.add(fasilitas);
            }
        }

        return daftarFasilitas;
    }

    public void tambahFasilitas(Fasilitas fasilitas) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = SqlConnect.getConnection();
            String sql = "INSERT INTO fasilitas (nama_fasilitas) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fasilitas.getNamaFasilitas());
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

    public void hapusFasilitas(int idFasilitas) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SqlConnect.getConnection();
            String sql = "DELETE FROM fasilitas WHERE id_fasilitas = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idFasilitas);
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
}
