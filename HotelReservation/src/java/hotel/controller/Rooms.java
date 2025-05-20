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
                case "checkAvailability":
                    handleCheckAvailability(request, response, session);
                    break;
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

    private void handleCheckAvailability(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException, SQLException {
        try {
            String checkInStr = request.getParameter("checkin");
            String checkOutStr = request.getParameter("checkout");
            String adultsStr = request.getParameter("adults");
            String childrenStr = request.getParameter("children");
            String[] fasilitasArr = request.getParameterValues("fasilitas");

            if (checkInStr == null || checkOutStr == null || adultsStr == null || childrenStr == null
                    || checkInStr.isEmpty() || checkOutStr.isEmpty() || adultsStr.isEmpty() || childrenStr.isEmpty()) {
                session.setAttribute("errorMsg", "Semua field harus diisi.");
                response.sendRedirect("Rooms");
                return;
            }

            int adults = Integer.parseInt(adultsStr);
            int children = Integer.parseInt(childrenStr);

            if (adults < 0 || children < 0) {
                session.setAttribute("errorMsg", "Jumlah tamu tidak boleh negatif.");
                response.sendRedirect("Rooms");
                return;
            }

            int totalGuest = adults + children;

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            sdf.setLenient(false);

            Date parsedCheckIn = sdf.parse(checkInStr);
            Date parsedCheckOut = sdf.parse(checkOutStr);

            if (!parsedCheckOut.after(parsedCheckIn)) {
                session.setAttribute("errorMsg", "Tanggal check-out harus setelah check-in.");
                response.sendRedirect("Rooms");
                return;
            }

            java.sql.Date checkIn = new java.sql.Date(parsedCheckIn.getTime());
            java.sql.Date checkOut = new java.sql.Date(parsedCheckOut.getTime());

            List<Integer> fasilitasIdList = new ArrayList<>();
            if (fasilitasArr != null) {
                for (String fId : fasilitasArr) {
                    try {
                        fasilitasIdList.add(Integer.parseInt(fId));
                    } catch (NumberFormatException ignored) {}
                }
            }

            List<KamarTersedia> hasilKamar = Kamar.cariKamarTersedia(checkIn, checkOut, totalGuest, fasilitasIdList);

            session.setAttribute("hasilKamar", hasilKamar);
            session.setAttribute("checkin", checkInStr);
            session.setAttribute("checkout", checkOutStr);
            session.setAttribute("adults", adults);
            session.setAttribute("children", children);
            session.setAttribute("fasilitasDipilih", fasilitasArr);
            session.setAttribute("successMsg", "Kamar tersedia broski.");

            response.sendRedirect("Rooms");

        } catch (ParseException e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Format tanggal tidak valid. Gunakan format seperti '15 May, 2025'.");
            response.sendRedirect("Rooms");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Input jumlah tamu atau fasilitas tidak valid.");
            response.sendRedirect("Rooms");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Terjadi kesalahan: " + e.getMessage());
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
        return "Rooms controller";
    }
}
