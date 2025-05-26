package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import java.util.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;
import org.mindrot.jbcrypt.BCrypt;

public class Customer extends User {

    private Reservasi reservasi;

    //Constructor
    public Customer(String nama, String email, String password) {
        super(nama, email, password);
    }

    public Customer() {
        super();
    }

    //getter & setter
    public Reservasi getReservasi() {
        return reservasi;
    }

    public void setReservasi(Reservasi Reservasi) {
        this.reservasi = Reservasi;
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
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, false)";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword); // simpan yang sudah di-hash

            return stmt.executeUpdate() > 0;
        }
    }

    public void buatReservasi(Reservasi reservasi) throws SQLException {
        String sql = "INSERT INTO reservasi (id_user, id_kamar, check_in, check_out, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservasi.getCustomer().getIdUser());
            stmt.setInt(2, reservasi.getKamar().getIdKamar());
            stmt.setDate(3, reservasi.getCheckIn());
            stmt.setDate(4, reservasi.getCheckOut());
            stmt.setString(5, reservasi.getStatus());

            stmt.executeUpdate();
        }

        this.reservasi = reservasi;
    }

    public List<Reservasi> lihatReservasi() throws SQLException {
        List<Reservasi> daftar = new ArrayList<>();

        String sql = "SELECT r.*, k.nomor_kamar, k.tipe "
                + "FROM reservasi r "
                + "JOIN kamar k ON r.id_kamar = k.id_kamar "
                + "WHERE r.id_user = ?";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.getIdUser());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idReservasi = rs.getInt("id_reservasi");
                int idKamar = rs.getInt("id_kamar");
                String nomorKamar = rs.getString("nomor_kamar");
                String tipeKamar = rs.getString("tipe");
                Date checkIn = rs.getDate("check_in");
                Date checkOut = rs.getDate("check_out");
                String status = rs.getString("status");

                // Bangun objek Kamar lengkap
                Kamar kamar = new Kamar();
                kamar.setIdKamar(idKamar);
                kamar.setNomorKamar(nomorKamar);
                kamar.setTipe(tipeKamar);

                // Bangun objek Reservasi
                Reservasi r = new Reservasi(idReservasi, this, kamar, checkIn, checkOut, status);
                daftar.add(r);
            }
        }

        return daftar;
    }

    //method batalkanReservasi
    public void batalkanReservasi(int idReservasi) throws SQLException {
        String updateReservasi = "UPDATE reservasi SET status = 'Dibatalkan' WHERE id_reservasi = ? AND id_user = ?";
        String updatePembayaran = "UPDATE pembayaran SET status = 'Dibatalkan' WHERE id_reservasi = ?";

        try (Connection conn = SqlConnect.getConnection()) {
            conn.setAutoCommit(false); // Mulai transaksi

            try (
                    PreparedStatement stmtReservasi = conn.prepareStatement(updateReservasi); PreparedStatement stmtPembayaran = conn.prepareStatement(updatePembayaran)) {
                // Update status reservasi
                stmtReservasi.setInt(1, idReservasi);
                stmtReservasi.setInt(2, this.getIdUser());
                stmtReservasi.executeUpdate();

                // Update status pembayaran (tidak perlu id_user karena relasi hanya lewat id_reservasi)
                stmtPembayaran.setInt(1, idReservasi);
                stmtPembayaran.executeUpdate();

                conn.commit(); // Commit jika semua berhasil
            } catch (SQLException e) {
                conn.rollback(); // Rollback jika ada error
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    //method ubahReservasi
    public void ubahReservasi(int idReservasi, Date newCheckIn, Date newCheckOut) throws SQLException {
        String sql = "UPDATE reservasi SET check_in = ?, check_out = ? WHERE id_reservasi = ? AND id_user = ?";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, newCheckIn);        // Tanggal check-in baru
            stmt.setDate(2, newCheckOut);       // Tanggal check-out baru
            stmt.setInt(3, idReservasi);        // ID reservasi
            stmt.setInt(4, this.getIdUser());   // ID user pemilik reservasi

            stmt.executeUpdate();
        }
    }
}
