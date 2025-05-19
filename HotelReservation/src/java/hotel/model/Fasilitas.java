package hotel.model;

import hotel.config.SqlConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;

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

    public void info(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("idFasilitas", getIdFasilitas());
        session.setAttribute("idFasilitas", getNamaFasilitas());
    }
}
