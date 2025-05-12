package hotel.model;

import java.time.LocalDate;

public class Pembayaran implements Bayarable {
    private String idPembayaran;
    private String metode;
    private double jumlahBayar;
    private String status;
    private LocalDate tanggalBayar;

    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran diproses...");
    }

    @Override
    public void tampilkanStruk() {
        System.out.println("Struk Pembayaran");
    }
}
