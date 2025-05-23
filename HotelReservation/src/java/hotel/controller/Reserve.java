package hotel.controller;

import hotel.helper.Fasilitas;
import hotel.helper.KamarTersedia;
import hotel.model.Kamar;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import hotel.model.*;

@WebServlet(name = "Reserve", urlPatterns = {"/Rooms/CheckAvailability/Reserve"})
public class Reserve extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            // Ambil parameter dari form
            String idKamarStr = request.getParameter("idKamar");
            if (idKamarStr == null || idKamarStr.isEmpty()) {
                session.setAttribute("errorMsg", "Kamar belum dipilih.");
                response.sendRedirect(request.getContextPath() + "/Rooms");
                return;
            }

            int idKamar = Integer.parseInt(idKamarStr);

            // Ambil data user yang sedang login
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                session.setAttribute("errorMsg", "Silakan login terlebih dahulu.");
                response.sendRedirect(request.getContextPath() + "/Accounts");
                return;
            }

            // Ambil data dari session (disimpan oleh CheckAvailability)
            String checkInStr = (String) session.getAttribute("checkin");
            String checkOutStr = (String) session.getAttribute("checkout");

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            Date parsedCheckIn = sdf.parse(checkInStr);
            Date parsedCheckOut = sdf.parse(checkOutStr);
            
            java.sql.Date checkIn = new java.sql.Date(parsedCheckIn.getTime());
            java.sql.Date checkOut = new java.sql.Date(parsedCheckOut.getTime());

            // Siapkan objek Customer dan Kamar
            Customer customer = new Customer();
            customer.setIdUser(userId); // Anda harus punya setter ini

            Kamar kamar = new Kamar();
            kamar.setIdKamar(idKamar); // Anda juga harus punya setter ini

            // Buat reservasi
            Reservasi reservasi = new Reservasi(0, customer, kamar, checkIn, checkOut, "Dipesan");
            customer.buatReservasi(reservasi);

            session.setAttribute("successMsg", "Reservasi berhasil dibuat!");
            response.sendRedirect(request.getContextPath() + "/Dashboard#reservasi");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Terjadi kesalahan saat membuat reservasi: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/Rooms");
        }
    }
}
