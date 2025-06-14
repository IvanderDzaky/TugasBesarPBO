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

    public static Pembayaran getByReservasi(int idReservasi) {
        Pembayaran bayar = null;
        try (Connection conn = SqlConnect.getConnection()) {
            String sql = "SELECT * FROM pembayaran WHERE id_reservasi = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReservasi);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bayar = new Pembayaran();
                bayar.setIdPembayaran(rs.getString("id_pembayaran"));
                bayar.setIdReservasi(rs.getInt("id_reservasi"));
                bayar.setJumlahBayar(rs.getDouble("jumlah_bayar"));
                bayar.setMetode(rs.getString("metode"));
                bayar.setStatus(rs.getString("status"));
                bayar.setTanggalBayar(rs.getTimestamp("tanggal_bayar"));
                bayar.setDeadLine(rs.getTimestamp("deadline"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bayar;
    }

    public static Pembayaran getByPembayaran(String idPembayaran) {
        Pembayaran bayar = null;
        try (Connection conn = SqlConnect.getConnection()) {
            String sql = "SELECT * FROM pembayaran WHERE id_pembayaran = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idPembayaran);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bayar = new Pembayaran();
                bayar.setIdPembayaran(rs.getString("id_pembayaran"));
                bayar.setIdReservasi(rs.getInt("id_reservasi"));
                bayar.setJumlahBayar(rs.getDouble("jumlah_bayar"));
                bayar.setMetode(rs.getString("metode"));
                bayar.setStatus(rs.getString("status"));
                bayar.setTanggalBayar(rs.getTimestamp("tanggal_bayar"));
                bayar.setDeadLine(rs.getTimestamp("deadline"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bayar;
    }

}
