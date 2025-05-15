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

    /**
     * Mendaftarkan customer ke database (isAdmin = false)
     */
    public boolean register() throws SQLException {
        String sql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, false)";
        try (Connection conn = SqlConnect.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, password);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Mengambil daftar reservasi berdasarkan id user
     */
    public void lihatReservasi(int idUser) {
        reservasiList.clear();
        String sql = "SELECT * FROM reservasi WHERE id_user = ?";

        try (Connection conn = SqlConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservasi r = new Reservasi(
                    rs.getInt("id_reservasi"),
                    rs.getInt("id_user"),
                    rs.getInt("id_kamar"),
                    rs.getDate("check_in"),
                    rs.getDate("check_out"),
                    rs.getString("status")
                );
                reservasiList.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Membuat reservasi baru untuk customer
     */
    public boolean buatReservasi(int idUser, int idKamar, Date checkIn, Date checkOut) {
        String sql = "INSERT INTO reservasi (id_user, id_kamar, check_in, check_out, status) VALUES (?, ?, ?, ?, 'Dipesan')";

        try (Connection conn = SqlConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idKamar);
            stmt.setDate(3, new java.sql.Date(checkIn.getTime()));
            stmt.setDate(4, new java.sql.Date(checkOut.getTime()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengubah tanggal check-in dan check-out reservasi selama status belum selesai
     */
    public boolean ubahReservasi(int idReservasi, Date newCheckIn, Date newCheckOut) {
        String sql = "UPDATE reservasi SET check_in = ?, check_out = ? WHERE id_reservasi = ? AND status = 'Dipesan'";

        try (Connection conn = SqlConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(newCheckIn.getTime()));
            stmt.setDate(2, new java.sql.Date(newCheckOut.getTime()));
            stmt.setInt(3, idReservasi);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Membatalkan reservasi jika status masih Dipesan
     */
    public boolean batalkanReservasi(int idReservasi) {
        String sql = "DELETE FROM reservasi WHERE id_reservasi = ? AND status = 'Dipesan'";

        try (Connection conn = SqlConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReservasi);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
