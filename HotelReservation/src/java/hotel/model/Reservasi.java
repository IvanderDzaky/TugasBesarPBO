package hotel.model;

import hotel.config.SqlConnect;
import java.time.temporal.ChronoUnit;
import java.sql.*;

public class Reservasi {

    private int idReservasi;
    private Kamar kamar;
    private Date checkIn;
    private Date checkOut;
    private Customer customer;
    private String status;

    // Constructor
    public Reservasi(int idReservasi, Customer customer, Kamar kamar, Date checkIn, Date checkOut, String status) {
        this.idReservasi = idReservasi;
        this.customer = customer;
        this.kamar = kamar;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Reservasi() {
    }

    // Getter dan Setter
    public int getIdReservasi() {
        return idReservasi;
    }

    public void setIdReservasi(int idReservasi) {
        this.idReservasi = idReservasi;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Menghitung durasi menginap (dalam hari)
    public int hitungDurasi() {
        if (checkIn != null && checkOut != null) {
            return (int) ChronoUnit.DAYS.between(
                    checkIn.toLocalDate(),
                    checkOut.toLocalDate()
            );
        }
        return 0;
    }

    // Menghitung total biaya = harga kamar × durasi
    public double hitungTotal() {
        int durasi = hitungDurasi();
        if (kamar != null && durasi > 0) {
            return kamar.getHarga() * durasi;
        }
        return 0;
    }

    // Menampilkan info reservasi (bisa dipakai untuk debugging atau log)
    public void tampilkanInfo() {
        System.out.println("=== Info Reservasi ===");
        System.out.println("ID Reservasi : " + idReservasi);
        System.out.println("Nama Customer: " + customer.getNama());
        System.out.println("Nomor Kamar  : " + kamar.getNomorKamar());
        System.out.println("Tipe Kamar   : " + kamar.getTipe());
        System.out.println("Check-In     : " + checkIn);
        System.out.println("Check-Out    : " + checkOut);
        System.out.println("Durasi       : " + hitungDurasi() + " malam");
        System.out.println("Harga Total  : Rp " + hitungTotal());
        System.out.println("Status       : " + status);
        System.out.println("=======================");
    }

    public static Reservasi getById(int id) {
        Reservasi reservasi = null;
        String query = "SELECT r.*, k.* FROM reservasi r JOIN kamar k ON r.id_kamar = k.id_kamar WHERE r.id_reservasi = ?";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reservasi = new Reservasi();
                reservasi.setIdReservasi(rs.getInt("id_reservasi"));
                reservasi.setCheckIn(rs.getDate("check_in"));
                reservasi.setCheckOut(rs.getDate("check_out"));
                reservasi.setStatus(rs.getString("status"));

                // Buat objek Kamar
                Kamar kamar = new Kamar();
                kamar.setIdKamar(rs.getInt("id_kamar"));
                kamar.setNomorKamar(rs.getString("nomor_kamar"));
                kamar.setTipe(rs.getString("tipe"));
                kamar.setHarga(rs.getDouble("harga"));

                reservasi.setKamar(kamar);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservasi;
    }

}
