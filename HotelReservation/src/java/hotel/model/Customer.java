package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import java.util.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;

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
        String sql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, false)";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, password);

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

}
