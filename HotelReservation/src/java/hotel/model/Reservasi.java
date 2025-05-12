package hotel.model;

import java.time.LocalDate;

public class Reservasi {

    private int idReservasi;
    private Customer customer;
    private Kamar kamar;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private Pembayaran pembayaran;

    public int hitungDurasi() {
        return (int) (checkOut.toEpochDay() - checkIn.toEpochDay());
    }

    public double hitungTotal() {
        return kamar.getHarga() * hitungDurasi();
    }

    public void tampilkanInfo() {
        System.out.println("Reservasi ID: " + idReservasi);
    }

    public int getIdReservasi() {
        return idReservasi;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
