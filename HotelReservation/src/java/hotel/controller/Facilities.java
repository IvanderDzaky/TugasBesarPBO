package hotel.controller;

import hotel.helper.Fasilitas;
import hotel.model.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Facilities", urlPatterns = {"/Facilities"})
public class Facilities extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
         Fasilitas fasilitas = new Fasilitas();
         tampilkanDaftarFasilitas(fasilitas,request);
         request.getRequestDispatcher("index.jsp?page=facilities").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private void tampilkanDaftarFasilitas( Fasilitas fasilitas, HttpServletRequest request)
            throws SQLException {
        List<Fasilitas> daftarFasilitas = fasilitas.lihatFasilitas();
        request.setAttribute("daftarFasilitas", daftarFasilitas);
    }
}
