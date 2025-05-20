package hotel.controller;

import hotel.helper.*;
import hotel.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            switch (action) {
                case "checkAvailability":
                    handleCheckAvailability(request, response);
                    break;
                default:
                    request.getSession().setAttribute("errorMsg", "Aksi tidak dikenali.");
                    response.sendRedirect("Admins");
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

    private void handleCheckAvailability(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            // Ambil parameter dari form
            String checkInStr = request.getParameter("checkin");
            String checkOutStr = request.getParameter("checkout");
            int adults = Integer.parseInt(request.getParameter("adults"));
            int children = Integer.parseInt(request.getParameter("children"));
            String[] fasilitasArr = request.getParameterValues("fasilitas");

            int totalGuest = adults + children;

            // Format tanggal yang diinput, contoh: "15 May, 2025"
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            sdf.setLenient(false); // supaya validasi tanggal lebih ketat

            java.util.Date parsedCheckIn = sdf.parse(checkInStr);
            java.util.Date parsedCheckOut = sdf.parse(checkOutStr);

            // Konversi ke java.sql.Date
            java.sql.Date checkIn = new java.sql.Date(parsedCheckIn.getTime());
            java.sql.Date checkOut = new java.sql.Date(parsedCheckOut.getTime());

            // Konversi fasilitas menjadi List<Integer>
            List<Integer> fasilitasIdList = new ArrayList<>();
            if (fasilitasArr != null) {
                for (String fId : fasilitasArr) {
                    fasilitasIdList.add(Integer.parseInt(fId));
                }
            }

            // Panggil method model
            List<KamarTersedia> hasilKamar = Kamar.cariKamarTersedia(checkIn, checkOut, totalGuest, fasilitasIdList);

            // Simpan data ke attribute
            request.setAttribute("hasilKamar", hasilKamar);
            request.setAttribute("checkin", checkInStr);
            request.setAttribute("checkout", checkOutStr);
            request.setAttribute("adults", adults);
            request.setAttribute("children", children);
            request.setAttribute("fasilitasDipilih", fasilitasArr); // supaya bisa ditampilkan ulang di form

            // Forward ke JSP
            request.getRequestDispatcher("index.jsp?page=rooms#kamar-tersedia").forward(request, response);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Format tanggal tidak valid. Harap gunakan format seperti '15 May, 2025'.");
            response.sendRedirect("Rooms");
        } catch (NullPointerException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Kesalahan sistem: Ada data yang kosong.");
            response.sendRedirect("Rooms");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Terjadi kesalahan: " + e.getClass().getSimpleName());
            response.sendRedirect("Rooms");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
