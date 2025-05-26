package hotel.controller;

import hotel.helper.Bayar;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class Dashboard extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");
            Customer customer = (Customer) user;

            if (action == null) {
                List<Reservasi> daftarReservasi = customer.lihatReservasi();
                Map<Integer, String> statusPembayaranMap = new HashMap<>();

                for (Reservasi r : daftarReservasi) {
                    int idReservasi = r.getIdReservasi();
                    String statusBayar = Bayar.getStatusPembayaranByReservasi(idReservasi);
                    statusPembayaranMap.put(idReservasi, statusBayar);
                }

                request.setAttribute("daftarReservasi", daftarReservasi);
                request.setAttribute("statusPembayaranMap", statusPembayaranMap);
                request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);
                return; // jangan lupa return supaya tidak lanjut ke switch
            }

            switch (action) {
                case "batalkanReservasi":
                    handleBatalkanReservasi(customer, request, response);
                    break;
                case "ubahReservasi":
                    handleUbahReservasi(customer, request, response);
                    break;
                default:
                    session.setAttribute("errorMsg", "Aksi tidak dikenali.");
                    response.sendRedirect("Dashboard");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan: " + e.getMessage());
            request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);
        }
    }

    protected void handleBatalkanReservasi(Customer customer, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idReservasiParam = request.getParameter("idReservasi");
            if (idReservasiParam == null) {
                request.setAttribute("errorMsg", "ID Reservasi tidak ditemukan.");
                processRequest(request, response);
                return;
            }

            int idReservasi = Integer.parseInt(idReservasiParam);
            customer.batalkanReservasi(idReservasi);

            HttpSession session = request.getSession();
            session.setAttribute("successMsg", "Reservasi berhasil dibatalkan.");

            // Redirect supaya POST tidak di-refresh (Post-Redirect-Get pattern)
            response.sendRedirect("Dashboard");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "ID Reservasi tidak valid.");
            request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Gagal membatalkan reservasi: " + e.getMessage());
            request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);

        }
    }

    protected void handleUbahReservasi(Customer customer, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idReservasiParam = request.getParameter("idReservasi");
            String newCheckInStr = request.getParameter("newCheckIn"); // perhatikan kapital 'I'
            String newCheckOutStr = request.getParameter("newCheckOut");

            if (idReservasiParam == null || newCheckInStr == null || newCheckOutStr == null) {
                request.setAttribute("errorMsg", "Data tidak lengkap untuk ubah reservasi.");
                processRequest(request, response);
                return;
            }

            int idReservasi = Integer.parseInt(idReservasiParam);

            // Parse tanggal
            java.util.Date parsedCheckIn = java.sql.Date.valueOf(newCheckInStr);
            java.util.Date parsedCheckOut = java.sql.Date.valueOf(newCheckOutStr);

            java.sql.Date newCheckIn = new java.sql.Date(parsedCheckIn.getTime());
            java.sql.Date newCheckOut = new java.sql.Date(parsedCheckOut.getTime());
            Date today = new Date(); // Hari ini (java.util.Date)

            if (parsedCheckIn.before(today)) {
                request.getSession().setAttribute("errorMsg", "Tanggal check-in tidak boleh di hari yang sudah lewat.");
                response.sendRedirect(request.getContextPath() + "/Dashboard");
                return;
            }
            if (!parsedCheckOut.after(parsedCheckIn)) {
                request.getSession().setAttribute("errorMsg", "Tanggal check-out harus setelah check-in.");
                response.sendRedirect(request.getContextPath() + "/Dashboard");
                return;
            }
            // Panggil method untuk update
            customer.ubahReservasi(idReservasi, newCheckIn, newCheckOut);

            // Sukses
            HttpSession session = request.getSession();
            session.setAttribute("successMsg", "Tanggal reservasi berhasil diubah.");
            response.sendRedirect("Dashboard");

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMsg", "Format tanggal tidak valid.");
            request.getRequestDispatcher("index.jsp?page=dashboard").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Gagal mengubah reservasi: " + e.getMessage());
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
