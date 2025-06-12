package hotel.model;

import hotel.helper.Fasilitas;
import hotel.config.SqlConnect;
import hotel.helper.Profit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

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
            String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
            stmt.setString(3, hashedPassword);

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

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
            String hashedPassword = BCrypt.hashpw(customerBaru.getPassword(), BCrypt.gensalt());
            stmt.setString(3, hashedPassword);
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
        String sql = "SELECT * FROM kamar ORDER BY nomor_kamar";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
        String sql = "SELECT * FROM fasilitas";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fasilitas fasilitas = new Fasilitas(
                        rs.getInt("id_fasilitas"),
                        rs.getString("nama_fasilitas"),
                        rs.getString("deskripsi_fasilitas")
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
            String sql = "INSERT INTO fasilitas (nama_fasilitas, deskripsi_fasilitas) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fasilitas.getNamaFasilitas());
            stmt.setString(2, fasilitas.getDeskripsiFasilitas());
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

    public List<Reservasi> lihatSemuaReservasi() throws SQLException {
        List<Reservasi> daftarReservasi = new ArrayList<>();

        String sql = "SELECT r.id_reservasi, r.check_in, r.check_out, r.status, "
                + "k.id_kamar, k.nomor_kamar, k.tipe, "
                + "u.id_user, u.nama, u.email "
                + "FROM reservasi r "
                + "JOIN kamar k ON r.id_kamar = k.id_kamar "
                + "JOIN users u ON r.id_user = u.id_user";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idReservasi = rs.getInt("id_reservasi");
                int idKamar = rs.getInt("id_kamar");
                int idUser = rs.getInt("id_user");
                java.sql.Date checkIn = rs.getDate("check_in");
                java.sql.Date checkOut = rs.getDate("check_out");
                String status = rs.getString("status");

                String nomorKamar = rs.getString("nomor_kamar");
                String tipeKamar = rs.getString("tipe");
                Kamar kamar = new Kamar();
                kamar.setIdKamar(idKamar);
                kamar.setNomorKamar(nomorKamar);
                kamar.setTipe(tipeKamar);

                String nama = rs.getString("nama");
                String email = rs.getString("email");
                Customer customer = new Customer();
                customer.setIdUser(idUser);
                customer.setNama(nama);
                customer.setEmail(email);

                Reservasi r = new Reservasi(idReservasi, customer, kamar, checkIn, checkOut, status);
                daftarReservasi.add(r);
            }
        }

        return daftarReservasi;
    }

    public void hapusReservasi(int idReservasi) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SqlConnect.getConnection();
            String sql = "DELETE FROM reservasi WHERE id_reservasi = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idReservasi);
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

    public void ubahReservasi(int idReservasi, Reservasi reservasi) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SqlConnect.getConnection();
            String sql = "UPDATE reservasi SET status = ? WHERE id_reservasi = ?";
            stmt = conn.prepareStatement(sql);

            // Perbaiki urutan parameter!
            stmt.setString(1, reservasi.getStatus());
            stmt.setInt(2, idReservasi);

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

    // Di class Admin (atau ProfitService), ubah method jadi:
    public List<Profit> lihatProfit() {
        List<Profit> profitList = new ArrayList<>();
        try (
                Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "SELECT YEAR(check_out) AS tahun, MONTH(check_out) AS bulan, SUM(k.harga) AS total_profit "
                + "FROM reservasi r "
                + "JOIN kamar k ON r.id_kamar = k.id_kamar "
                + "WHERE r.status = 'Selesai' "
                + "GROUP BY YEAR(check_out), MONTH(check_out) "
                + "ORDER BY tahun, bulan"
        ); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int tahun = rs.getInt("tahun");
                int bulan = rs.getInt("bulan");
                double total = rs.getDouble("total_profit");
                profitList.add(new Profit(tahun, bulan, total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profitList;
    }

}
