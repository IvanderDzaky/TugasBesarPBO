package hotel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import hotel.model.*;

@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class Dashboard extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("user");

            if (customer != null) {
                List<Reservasi> daftarReservasi = customer.lihatReservasi(); // Ambil reservasi
                request.setAttribute("daftarReservasi", daftarReservasi);    // Kirim ke JSP
            } else {
                request.setAttribute("errorMsg", "Silakan login untuk melihat dashboard.");
                response.sendRedirect("Accounts");
                return;
            }

            request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan: " + e.getMessage());
            request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);
        }
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
}
