package hotel.helper;

import hotel.config.SqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import hotel.model.Pembayaran;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Bayar {

    public static boolean simpanPembayaran(Pembayaran p) {
        boolean sukses = false;
        try (Connection conn = SqlConnect.getConnection()) {
            String sql = "INSERT INTO pembayaran (id_pembayaran, id_reservasi, metode, jumlah_bayar, status, deadline) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getIdPembayaran());
            ps.setInt(2, p.getIdReservasi());
            ps.setString(3, p.getMetode());
            ps.setDouble(4, p.getJumlahBayar());
            ps.setString(5, p.getStatus());
            ps.setTimestamp(6, p.getDeadLine());
            sukses = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sukses;
    }

    public static boolean konfirmasiPembayaran(Pembayaran pembayaran) {
        boolean result = false;
        try (Connection conn = SqlConnect.getConnection()) {
            String sql = "UPDATE pembayaran SET status = ?, tanggal_bayar = ? WHERE id_pembayaran = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pembayaran.getStatus());
            ps.setTimestamp(2, pembayaran.getTanggalBayar());
            ps.setString(3, pembayaran.getIdPembayaran());

            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getStatusPembayaranByReservasi(int idReservasi) {
        String status = "Belum Ada Pembayaran"; // Default jika belum ada pembayaran
        try (Connection conn = SqlConnect.getConnection()) {
            String sql = "SELECT status FROM pembayaran WHERE id_reservasi = ? ORDER BY tanggal_bayar DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idReservasi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
