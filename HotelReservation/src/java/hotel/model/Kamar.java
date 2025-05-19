package hotel.model;

import hotel.config.SqlConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import java.sql.*;

public class Kamar {

    private int idKamar;
    private String nomorKamar;
    private String tipe;
    private double harga;
    private int maxGuest;
    private boolean isTersedia;
    private List<Fasilitas> fasilitasList;

    // Constructor
    public Kamar(String nomorKamar, String tipe, double harga, boolean isTersedia, int maxGuest) {
        this.nomorKamar = nomorKamar;
        this.tipe = tipe;
        this.harga = harga;
        this.isTersedia = isTersedia;
        this.maxGuest = maxGuest;
    }

    // Getter & Setter
    public List<Fasilitas> getFasilitasList() {
        return fasilitasList;
    }

    public void setFasilitasList(List<Fasilitas> fasilitasList) {
        this.fasilitasList = fasilitasList;
    }

    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
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

    public int getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public boolean isTersedia() {
        return isTersedia;
    }

    public void setTersedia(boolean isTersedia) {
        this.isTersedia = isTersedia;
    }

    // Method tampilkanInfo()
    public void info(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("idKamar", getIdKamar());
        session.setAttribute("nomorKamar", getNomorKamar());
        session.setAttribute("tipeKamar", getTipe());
        session.setAttribute("hargaKamar", getHarga());
        session.setAttribute("maxGuest", getMaxGuest());
        session.setAttribute("isTersedia", isTersedia());
        session.setAttribute("fasilitas", getFasilitasList());
    }

    // Method ubahStatus()
    public void ubahStatus(boolean status) {
        this.isTersedia = status;
    }

    // Method tambah Fasilitas
    public void tambahFasilitas(Fasilitas fasilitas) {
        this.fasilitasList.add(fasilitas);
    }

    //method lihatFasilitasKamar()
    public List<Fasilitas> lihatFasilitasKamar() throws SQLException {
        List<Fasilitas> fasilitasKamar = new ArrayList<>();
        Connection conn = SqlConnect.getConnection();

        String fasilitasQuery = "SELECT f.id_fasilitas, f.nama_fasilitas FROM fasilitas f "
                + "JOIN kamar_fasilitas kf ON f.id_fasilitas = kf.id_fasilitas "
                + "WHERE kf.id_kamar = ?";

        try (PreparedStatement fasilitasStmt = conn.prepareStatement(fasilitasQuery)) {
            fasilitasStmt.setInt(1, this.idKamar); // pakai id dari objek sekarang
            try (ResultSet frs = fasilitasStmt.executeQuery()) {
                while (frs.next()) {
                    Fasilitas f = new Fasilitas(
                            frs.getInt("id_fasilitas"),
                            frs.getString("nama_fasilitas")
                    );
                    fasilitasKamar.add(f);
                }
            }
        }

        return fasilitasKamar;
    }

}
