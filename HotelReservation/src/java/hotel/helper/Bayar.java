package hotel.helper;

import hotel.config.SqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.UUID;

public class Bayar {

    public static boolean simpanPembayaran(int idReservasi, String metode, double jumlahBayar) {
        String sql = "INSERT INTO pembayaran (id_pembayaran, id_reservasi, metode, jumlah_bayar, status, tanggal_bayar) "
                + "VALUES (?, ?, ?, ?, ?, CURDATE())";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String idPembayaran = "PAY-" + UUID.randomUUID().toString();

            stmt.setString(1, idPembayaran);
            stmt.setInt(2, idReservasi);
            stmt.setString(3, metode);
            stmt.setDouble(4, jumlahBayar);
            stmt.setString(5, "Pending");

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getStatusPembayaranByReservasi(int idReservasi) throws Exception {
        Connection conn = SqlConnect.getConnection();
        String sql = "SELECT status FROM pembayaran WHERE id_reservasi = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idReservasi);
        ResultSet rs = stmt.executeQuery();
        String status = "Belum Bayar"; // default
        if (rs.next()) {
            status = rs.getString("status");
        }
        rs.close();
        stmt.close();
        conn.close();
        return status;
    }

    public static String getIdPembayaranByReservasi(int idReservasi) {
        String id = null;
        String sql = "SELECT id_pembayaran FROM pembayaran WHERE id_reservasi = ? ORDER BY id_pembayaran DESC LIMIT 1";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReservasi);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getString("id_pembayaran");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void updateStatusLunas(String idPembayaran) {
        String sql = "UPDATE pembayaran SET status = 'lunas', tanggal_bayar = CURDATE() WHERE id_pembayaran = ?";
        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPembayaran);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
