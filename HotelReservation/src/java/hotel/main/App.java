package hotel.main;

import hotel.model.*;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.setNama("Admin Hotel");
        admin.setEmail("admin@hotel.com");

        Customer customer = new Customer();
        customer.setNama("Adit");
        customer.setEmail("adit@email.com");

        admin.tambahCustomer(customer);

        Kamar kamar = new Kamar();
        kamar.setNomorKamar("D101");
        kamar.setHarga(300000);
        kamar.setTipe("Deluxe");
        kamar.ubahStatus(true);

        admin.tambahKamar(kamar);

        Reservasi reservasi = new Reservasi();
        reservasi.setCustomer(customer);
        reservasi.setKamar(kamar);
        reservasi.setCheckIn(LocalDate.of(2024, 6, 10));
        reservasi.setCheckOut(LocalDate.of(2024, 6, 13));

        System.out.println("Durasi: " + reservasi.hitungDurasi() + " malam");
        System.out.println("Total Harga: Rp " + reservasi.hitungTotal());

        customer.buatReservasi(reservasi);
        customer.lihatReservasi();
    }
}
