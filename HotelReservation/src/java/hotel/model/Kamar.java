package hotel.model;

public class Kamar {

    private String nomorKamar;
    private String tipe;
    private double harga;
    private boolean tersedia;

    public void tampilkanInfo() {
    }

    public void ubahStatus(boolean status) {
        this.tersedia = status;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public double getHarga() {
        return harga;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

}
