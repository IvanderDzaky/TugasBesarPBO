package hotel.model;

import java.sql.Date;

public class Reservasi {

    private int idReservasi;
    private int idUser;
    private int idKamar;
    private Date checkIn;
    private Date checkOut;
    private String status;

    public Reservasi(int idReservasi, int idUser, int idKamar, Date checkIn, Date checkOut, String status) {
        this.idReservasi = idReservasi;
        this.idUser = idUser;
        this.idKamar = idKamar;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
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
}
