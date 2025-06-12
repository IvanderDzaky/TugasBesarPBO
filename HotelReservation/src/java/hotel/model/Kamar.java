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

        String fasilitasQuery = "SELECT f.id_fasilitas, f.nama_fasilitas FROM fasilitas f "
                + "JOIN kamar_fasilitas kf ON f.id_fasilitas = kf.id_fasilitas "
                + "WHERE kf.id_kamar = ?";

        try (Connection conn = SqlConnect.getConnection(); PreparedStatement fasilitasStmt = conn.prepareStatement(fasilitasQuery)) {

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
        boolean success = false;

        String insertKamarSQL = "INSERT INTO kamar (nomor_kamar, tipe, harga, status, max_guest) VALUES (?, ?, ?, ?, ?)";
        String insertFasilitasSQL = "INSERT INTO kamar_fasilitas (id_kamar, id_fasilitas) VALUES (?, ?)";

        try (Connection conn = SqlConnect.getConnection()) {
            conn.setAutoCommit(false); // Mulai transaksi

            try (
                    PreparedStatement ps = conn.prepareStatement(insertKamarSQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nomorKamar);
                ps.setString(2, tipe);
                ps.setDouble(3, harga);
                ps.setInt(4, isTersedia ? 1 : 0);
                ps.setInt(5, maxGuest);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idKamar = rs.getInt(1);
                    }
                }

                if (fasilitasList != null && !fasilitasList.isEmpty()) {
                    try (PreparedStatement fasilitasStmt = conn.prepareStatement(insertFasilitasSQL)) {
                        for (Fasilitas f : fasilitasList) {
                            fasilitasStmt.setInt(1, idKamar);
                            fasilitasStmt.setInt(2, f.getIdFasilitas());
                            fasilitasStmt.addBatch();
                        }
                        fasilitasStmt.executeBatch();
                    }
                }

                conn.commit(); // Commit transaksi jika semua berhasil
                success = true;

            } catch (SQLException e) {
                conn.rollback(); // Rollback kalau ada error
                throw e;
            } finally {
                conn.setAutoCommit(true); // Balikkan ke default
            }

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

    public static List<Fasilitas> getFasilitasByTipe(String tipe) {
        List<Fasilitas> list = new ArrayList<>();
        try (Connection conn = SqlConnect.getConnection()) {
            String sql = "SELECT f.id_fasilitas, f.nama_fasilitas FROM fasilitas f "
                    + "JOIN kamar_fasilitas kf ON f.id_fasilitas = kf.id_fasilitas "
                    + "JOIN kamar k ON kf.id_kamar = k.id_kamar "
                    + "WHERE k.tipe = ? "
                    + "GROUP BY f.id_fasilitas, f.nama_fasilitas";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, tipe);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Fasilitas f = new Fasilitas();
                    f.setIdFasilitas(rs.getInt("id_fasilitas"));
                    f.setNamaFasilitas(rs.getString("nama_fasilitas"));
                    list.add(f);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<KamarTersedia> cariKamarTersedia(Date checkIn, Date checkOut, int guest, List<Integer> idFasilitas) {
        List<KamarTersedia> hasil = new ArrayList<>();

        try (Connection conn = SqlConnect.getConnection()) {
            // Buat placeholder ? sebanyak jumlah fasilitas untuk IN clause
            StringBuilder fasilitasPlaceholder = new StringBuilder();
            if (idFasilitas != null && !idFasilitas.isEmpty()) {
                for (int i = 0; i < idFasilitas.size(); i++) {
                    fasilitasPlaceholder.append("?");
                    if (i < idFasilitas.size() - 1) {
                        fasilitasPlaceholder.append(", ");
                    }
                }
            }

            String sql = "SELECT "
                    + "    k.id_kamar, "
                    + "    k.tipe, "
                    + "    k.harga, "
                    + "    k.max_guest, "
                    + "    COUNT(k.id_kamar) AS jumlah_tersedia "
                    + "FROM "
                    + "    kamar k "
                    + "WHERE "
                    + "    k.status = 1 "
                    + "    AND k.max_guest >= ? "
                    + "    AND k.id_kamar NOT IN ( "
                    + "        SELECT r.id_kamar FROM reservasi r "
                    + "        WHERE NOT (r.check_out <= ? OR r.check_in >= ?) "
                    + "        AND r.status = 'Dipesan' "
                    + "    ) ";

            if (idFasilitas != null && !idFasilitas.isEmpty()) {
                sql += " AND k.id_kamar IN ( "
                        + "    SELECT kf.id_kamar "
                        + "    FROM kamar_fasilitas kf "
                        + "    WHERE kf.id_fasilitas IN (" + fasilitasPlaceholder + ") "
                        + "    GROUP BY kf.id_kamar "
                        + "    HAVING COUNT(DISTINCT kf.id_fasilitas) = ? "
                        + ") ";
            }

            sql += "GROUP BY k.tipe, k.harga, k.max_guest";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                int paramIndex = 1;

                // 1. set max_guest
                stmt.setInt(paramIndex++, guest);

                // 2. set checkIn (untuk cek reservasi overlap)
                stmt.setDate(paramIndex++, checkIn);

                // 3. set checkOut (untuk cek reservasi overlap)
                stmt.setDate(paramIndex++, checkOut);

                // 4. set idFasilitas jika ada
                if (idFasilitas != null && !idFasilitas.isEmpty()) {
                    for (Integer id : idFasilitas) {
                        stmt.setInt(paramIndex++, id);
                    }
                    // 5. set jumlah fasilitas untuk HAVING count()
                    stmt.setInt(paramIndex++, idFasilitas.size());
                }

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    KamarTersedia kt = new KamarTersedia();
                    kt.setIdKamar(rs.getInt("id_kamar"));
                    kt.setTipe(rs.getString("tipe"));
                    kt.setHarga(rs.getDouble("harga"));
                    kt.setMaxGuest(rs.getInt("max_guest"));
                    kt.setJumlahTersedia(rs.getInt("jumlah_tersedia"));
                    List<Fasilitas> fasilitasList = getFasilitasByTipe(kt.getTipe());
                    kt.setFasilitasList(fasilitasList);
                    hasil.add(kt);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasil;
    }

}
