package hotel.model;

import hotel.helper.Fasilitas;
import hotel.helper.KamarTersedia;
import hotel.config.SqlConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import java.sql.*;
import java.sql.Date;

public class Kamar {

    private int idKamar;
    private String nomorKamar;
    private String tipe;
    private double harga;
    private int maxGuest;
    private boolean isTersedia;
    private List<Fasilitas> fasilitasList;

    // Constructor
    public Kamar() {
    }

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

    public boolean saveToDB() throws SQLException {
        Connection conn = SqlConnect.getConnection();
        boolean success = false;

        try {
            String insertKamarSQL = "INSERT INTO kamar (nomor_kamar, tipe, harga, status, max_guest) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertKamarSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nomorKamar);
            ps.setString(2, tipe);
            ps.setDouble(3, harga);
            ps.setInt(4, isTersedia ? 1 : 0);
            ps.setInt(5, maxGuest);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idKamar = rs.getInt(1);
            }

            if (fasilitasList != null) {
                for (Fasilitas f : fasilitasList) {
                    PreparedStatement fasilitasStmt = conn.prepareStatement(
                            "INSERT INTO kamar_fasilitas (id_kamar, id_fasilitas) VALUES (?, ?)");
                    fasilitasStmt.setInt(1, idKamar);
                    fasilitasStmt.setInt(2, f.getIdFasilitas());
                    fasilitasStmt.executeUpdate();
                }
            }

            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean updateToDB() throws SQLException {
        Connection conn = null;
        boolean success = false;

        try {
            conn = SqlConnect.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Update data utama kamar
            String updateSQL = "UPDATE kamar SET nomor_kamar = ?, tipe = ?, harga = ?, status = ?, max_guest = ? WHERE id_kamar = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
                ps.setString(1, nomorKamar);
                ps.setString(2, tipe);
                ps.setDouble(3, harga);
                ps.setInt(4, isTersedia ? 1 : 0);
                ps.setInt(5, maxGuest);
                ps.setInt(6, idKamar);
                ps.executeUpdate();
            }

            // 2. Hapus relasi fasilitas lama
            try (PreparedStatement deleteFasilitas = conn.prepareStatement(
                    "DELETE FROM kamar_fasilitas WHERE id_kamar = ?")) {
                deleteFasilitas.setInt(1, idKamar);
                deleteFasilitas.executeUpdate();
            }

            // 3. Insert fasilitas baru dengan BATCH
            if (fasilitasList != null && !fasilitasList.isEmpty()) {
                try (PreparedStatement fasilitasStmt = conn.prepareStatement(
                        "INSERT INTO kamar_fasilitas (id_kamar, id_fasilitas) VALUES (?, ?)")) {

                    for (Fasilitas f : fasilitasList) {
                        fasilitasStmt.setInt(1, idKamar);
                        fasilitasStmt.setInt(2, f.getIdFasilitas());
                        fasilitasStmt.addBatch();
                    }
                    fasilitasStmt.executeBatch();
                }
            }

            conn.commit(); // Commit jika semua sukses
            success = true;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback jika error
            }
            e.printStackTrace();
            throw e; // Re-throw exception untuk handling di layer atas
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Reset auto-commit
                conn.close();
            }
        }

        return success;
    }

    public static boolean deleteFromDB(int idKamar) throws SQLException {
        Connection conn = null;
        boolean success = false;

        try {
            conn = SqlConnect.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Hapus relasi fasilitas terlebih dahulu
            try (PreparedStatement delFasilitas = conn.prepareStatement(
                    "DELETE FROM kamar_fasilitas WHERE id_kamar = ?")) {
                delFasilitas.setInt(1, idKamar);
                delFasilitas.executeUpdate();
            }

            // 2. Hapus kamar
            try (PreparedStatement delKamar = conn.prepareStatement(
                    "DELETE FROM kamar WHERE id_kamar = ?")) {
                delKamar.setInt(1, idKamar);
                int affectedRows = delKamar.executeUpdate();

                if (affectedRows == 0) {
                    conn.rollback();
                    return false; // Kamar tidak ditemukan
                }
            }

            conn.commit();
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e; // Re-throw exception untuk handling di layer controller
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }



}
