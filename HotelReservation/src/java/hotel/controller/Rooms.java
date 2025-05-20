package hotel.controller;

import hotel.model.Admin;
import hotel.model.Fasilitas;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Rooms", urlPatterns = {"/Rooms"})
public class Rooms extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            if (action == null) {
                tampilkanDaftarFasilitas(request);
                request.getRequestDispatcher("index.jsp?page=rooms").forward(request, response);
                return;
            }
            switch(action){
                case "checkAvailability":
                    
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan saat memproses permintaan.");
            request.getRequestDispatcher("index.jsp?page=rooms").forward(request, response);
        }
    }

    private void tampilkanDaftarFasilitas(HttpServletRequest request)
            throws SQLException {
        List<Fasilitas> daftar = Fasilitas.lihatFasilitas();
        request.setAttribute("daftarFasilitas", daftar);
    }

    private void handleCheckAvailabilty(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
