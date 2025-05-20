package hotel.helper;

import hotel.config.SqlConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Fasilitas {

    private int idFasilitas;
    private String namaFasilitas;

    // Constructor
    public Fasilitas() {
    }

    public Fasilitas(int idFasilitas, String namaFasilitas) {
        this.idFasilitas = idFasilitas;
        this.namaFasilitas = namaFasilitas;
    }

    public Fasilitas(String namaFasilitas) {
        this.namaFasilitas = namaFasilitas;
    }

    // Getter & Setter
    public int getIdFasilitas() {
        return idFasilitas;
    }

    public void setIdFasilitas(int idFasilitas) {
        this.idFasilitas = idFasilitas;
    }

    public String getNamaFasilitas() {
        return namaFasilitas;
    }

    public void setNamaFasilitas(String namaFasilitas) {
        this.namaFasilitas = namaFasilitas;
    }


    public static List<Fasilitas> lihatFasilitas() throws SQLException {
        List<Fasilitas> daftarFasilitas = new ArrayList<>();
        Connection conn = SqlConnect.getConnection();
        String sql = "SELECT * FROM fasilitas";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fasilitas fasilitas = new Fasilitas(
                        rs.getInt("id_fasilitas"),
                        rs.getString("nama_fasilitas")
                );
                daftarFasilitas.add(fasilitas);
            }
        }

        return daftarFasilitas;
    }
}
