package hotel.model;

import java.sql.Date;
import java.time.LocalDate;

public class Pembayaran implements Bayarable {

    private int idReservasi;
    private String idPembayaran;
    private String metode;
    private double jumlahBayar;
    private String status;
    private Date tanggalBayar;

    // Constructor
    public Pembayaran() {
    }

    public Pembayaran(String idPembayaran, String metode, double jumlahBayar, String status, Date tanggalBayar) {
        this.idPembayaran = idPembayaran;
        this.metode = metode;
        this.jumlahBayar = jumlahBayar;
        this.status = status;
        this.tanggalBayar = tanggalBayar;
    }

    // Getter & Setter
    public int getIdReservasi() {
        return idReservasi;
    }

    public void setIdReservasi(int idReservasi) {
        this.idReservasi = idReservasi;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Date tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    // ====================
    // Method dari interface Bayarable
    // ====================
    /**
     * Menjalankan simulasi pembayaran: mengubah status menjadi "Lunas" dan
     * mengisi tanggal bayar hari ini.
     */
    @Override
    public void prosesPembayaran() {
        if (this.jumlahBayar <= 0) {
            System.out.println("Jumlah pembayaran tidak valid.");
            return;
        }
        this.status = "Lunas";
        this.tanggalBayar = Date.valueOf(LocalDate.now());
    }

    /**
     * Menampilkan struk pembayaran ke konsol (bisa juga diubah jadi return
     * String untuk ditampilkan di JSP).
     */
    @Override
    public void tampilkanStruk() {
        System.out.println("========== STRUK PEMBAYARAN ==========");
        System.out.println("ID Pembayaran : " + idPembayaran);
        System.out.println("Metode        : " + metode);
        System.out.println("Jumlah Dibayar: Rp " + jumlahBayar);
        System.out.println("Status        : " + status);
        System.out.println("Tanggal Bayar : " + (tanggalBayar != null ? tanggalBayar : "Belum dibayar"));
        System.out.println("======================================");
    }

    // Bisa juga tambah method lain jika perlu, seperti validasi, dsb.
    @Override
    public String toString() {
        return "Pembayaran{"
                + "idPembayaran=" + idPembayaran
                + ", idReservasi=" + idReservasi
                + ", metode='" + metode + '\''
                + ", jumlahBayar=" + jumlahBayar
                + ", status='" + status + '\''
                + ", tanggalBayar=" + tanggalBayar
                + '}';
    }

}
