package hotel.model;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Pembayaran implements Bayarable {

    private int idReservasi;
    private String idPembayaran;
    private String metode;
    private double jumlahBayar;
    private String status;
    private Timestamp tanggalBayar;
    private Timestamp deadLine;

    // Constructor
    public Pembayaran() {
    }

    public Pembayaran(int idReservasi, String idPembayaran, String metode, double jumlahBayar, String status, Timestamp tanggalBayar, Timestamp deadLine) {
        this.idReservasi = idReservasi;
        this.idPembayaran = idPembayaran;
        this.metode = metode;
        this.jumlahBayar = jumlahBayar;
        this.status = status;
        this.tanggalBayar = tanggalBayar;
        this.deadLine = deadLine;
    }

    public Timestamp getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Timestamp deadLine) {
        this.deadLine = deadLine;
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

    public Timestamp getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Timestamp tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    @Override
    public void prosesPembayaran() {
        if (this.jumlahBayar <= 0) {
            System.out.println("Jumlah pembayaran tidak valid.");
            return;
        }
        this.status = "Lunas";
        this.tanggalBayar = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public Map<String, Object> tampilkanStruk() {
        Map<String, Object> data = new HashMap<>();
        Reservasi reservasi = new Reservasi().getById(this.getIdReservasi());
        Kamar kamar = reservasi.getKamar();

        long durasi = ChronoUnit.DAYS.between(
                reservasi.getCheckIn().toLocalDate(),
                reservasi.getCheckOut().toLocalDate()
        );

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        String formattedTotal = formatter.format(this.getJumlahBayar());

        data.put("pembayaran", this);
        data.put("kamar", kamar);
        data.put("durasi", durasi);
        data.put("formattedTotal", formattedTotal);

        return data;
    }

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

    public boolean isValid() {
        return idPembayaran != null && !idPembayaran.isEmpty()
                && idReservasi > 0
                && jumlahBayar > 0;
    }

}
