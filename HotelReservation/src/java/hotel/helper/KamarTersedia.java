package hotel.helper;

import java.util.List;
import hotel.model.*;
public class KamarTersedia {

    private String tipe;
    private double harga;
    private int maxGuest;
    private int jumlahTersedia;
    private List<Fasilitas> fasilitasList;

    public KamarTersedia() {
        // constructor kosong
    }

    public KamarTersedia(String tipe, int harga, int maxGuest, int jumlahTersedia, List<Fasilitas> fasilitasList) {
        this.tipe = tipe;
        this.harga = harga;
        this.maxGuest = maxGuest;
        this.jumlahTersedia = jumlahTersedia;
        this.fasilitasList = fasilitasList;
    }

    public void setFasilitasList(List<Fasilitas> fasilitasList) {
        this.fasilitasList = fasilitasList;
    }

    public List<Fasilitas> getFasilitasList() {
        return fasilitasList;
    }

    public String getTipe() {
        return tipe;
    }

    public double getHarga() {
        return harga;
    }

    public int getMaxGuest() {
        return maxGuest;
    }

    public int getJumlahTersedia() {
        return jumlahTersedia;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

}
