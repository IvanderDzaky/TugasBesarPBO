package hotel.helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarTersedia {

    private int idKamar;
    private String tipe;
    private double harga;
    private int jumlahTersedia;
    private int maxGuest;
    private List<Fasilitas> fasilitasList;  // untuk simpan fasilitas kamar

    // Constructor kosong
    public KamarTersedia() {
    }

    // Constructor lengkap (optional)
    public KamarTersedia(int idKamar, String tipe, double harga, int jumlahTersedia, int maxGuest) {
        this.idKamar = idKamar;
        this.tipe = tipe;
        this.harga = harga;
        this.jumlahTersedia = jumlahTersedia;
        this.maxGuest = maxGuest;
    }

    // Getter & Setter
    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getJumlahTersedia() {
        return jumlahTersedia;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

    public int getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public List<Fasilitas> getFasilitasList() {
        return fasilitasList;
    }

    public void setFasilitasList(List<Fasilitas> fasilitasList) {
        this.fasilitasList = fasilitasList;
    }

}
