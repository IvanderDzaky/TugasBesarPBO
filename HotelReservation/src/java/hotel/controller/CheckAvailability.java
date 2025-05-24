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

@WebServlet(name = "CheckAvailability", urlPatterns = {"/Rooms/CheckAvailability"})
public class CheckAvailability extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        tampilkanDaftarFasilitas(request);

        if (!handleCheckAvailability(request, response, session)) {
            return; // Redirect sudah dilakukan, hentikan eksekusi
        }

        request.getRequestDispatcher("/index.jsp?page=rooms#kamar-tersedia").forward(request, response);
    }

    private void tampilkanDaftarFasilitas(HttpServletRequest request)
            throws SQLException {
        List<Fasilitas> daftar = Fasilitas.lihatFasilitas();
        request.setAttribute("daftarFasilitas", daftar);
    }

    private boolean handleCheckAvailability(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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
                response.sendRedirect(request.getContextPath() + "/Rooms");
                return false;
            }

            int adults = Integer.parseInt(adultsStr);
            int children = Integer.parseInt(childrenStr);

            if (adults < 0 || children < 0) {
                session.setAttribute("errorMsg", "Jumlah tamu tidak boleh negatif.");
                response.sendRedirect(request.getContextPath() + "/Rooms");
                return false;
            }

            int totalGuest = adults + children;

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            sdf.setLenient(false);

            Date parsedCheckIn = sdf.parse(checkInStr);
            Date parsedCheckOut = sdf.parse(checkOutStr);
            
            Date today = new Date(); // Hari ini (java.util.Date)

            if (parsedCheckIn.before(today)) {
                session.setAttribute("errorMsg", "Tanggal check-in tidak boleh di hari yang sudah lewat.");
                response.sendRedirect(request.getContextPath() + "/Rooms");
                return false;
            }

            if (!parsedCheckOut.after(parsedCheckIn)) {
                session.setAttribute("errorMsg", "Tanggal check-out harus setelah check-in.");
                response.sendRedirect(request.getContextPath() + "/Rooms");
                return false;
            }

            java.sql.Date checkIn = new java.sql.Date(parsedCheckIn.getTime());
            java.sql.Date checkOut = new java.sql.Date(parsedCheckOut.getTime());

            List<Integer> fasilitasIdList = new ArrayList<>();
            if (fasilitasArr != null) {
                for (String fId : fasilitasArr) {
                    try {
                        fasilitasIdList.add(Integer.parseInt(fId));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            List<KamarTersedia> hasilKamar = Kamar.cariKamarTersedia(checkIn, checkOut, totalGuest, fasilitasIdList);

            request.setAttribute("hasilKamar", hasilKamar);
            session.setAttribute("checkin", checkInStr);
            session.setAttribute("checkout", checkOutStr);
            session.setAttribute("adults", adults);
            session.setAttribute("children", children);
            session.setAttribute("fasilitasDipilih", fasilitasArr);
            session.setAttribute("successMsg", "Kamar tersedia broski.");
            return true;

        } catch (ParseException e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Format tanggal tidak valid. Gunakan format seperti '15 May, 2025'.");
            response.sendRedirect(request.getContextPath() + "/Rooms");
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Input jumlah tamu atau fasilitas tidak valid.");
            response.sendRedirect(request.getContextPath() + "/Rooms");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Terjadi kesalahan: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/Rooms");
            return false;
        }
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
    }// </editor-fold>

}
