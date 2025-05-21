package hotel.controller;

import hotel.helper.*;
import hotel.model.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "Rooms", urlPatterns = {"/Rooms"})
public class Rooms extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            String action = request.getParameter("action");
            if (action == null) {
                tampilkanDaftarFasilitas(request);
                request.getRequestDispatcher("index.jsp?page=rooms").forward(request, response);
                return;
            }

            switch (action) {
                default:
                    session.setAttribute("errorMsg", "Aksi tidak dikenali.");
                    response.sendRedirect("Rooms");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Terjadi kesalahan saat memproses permintaan.");
            response.sendRedirect("Rooms");
        }
    }

    private void tampilkanDaftarFasilitas(HttpServletRequest request)
            throws SQLException {
        List<Fasilitas> daftar = Fasilitas.lihatFasilitas();
        request.setAttribute("daftarFasilitas", daftar);
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
        return "Rooms controller";
    }
}
