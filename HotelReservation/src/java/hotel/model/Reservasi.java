package hotel.model;

import hotel.config.SqlConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    //method
   

}
