package hotel.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import hotel.helper.Bayar;
import hotel.model.Kamar;
import hotel.model.Reservasi;

@WebServlet(name = "Payment", urlPatterns = {"/Payment"})
public class Payment extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("idReservasi");
        int idReservasi = 0;

        try {
            idReservasi = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID reservasi tidak valid");
            return;
        }

        Reservasi reservasi = Reservasi.getById(idReservasi);
        if (reservasi == null) {
            response.sendRedirect("index.jsp?page=404");
            return;
        }

        Kamar kamar = reservasi.getKamar();
        int durasi = reservasi.hitungDurasi();
        double total = reservasi.hitungTotal();

        request.setAttribute("reservasi", reservasi);
        request.setAttribute("kamar", kamar);
        request.setAttribute("durasi", durasi);
        request.setAttribute("totalHarga", total);

        boolean isConfirmClicked = request.getParameter("fromConfirm") != null;

        if (isConfirmClicked) {
            String idPembayaran = Bayar.getIdPembayaranByReservasi(idReservasi);
            if (idPembayaran != null) {
                request.setAttribute("showCountdown", true);
                request.setAttribute("idPembayaran", idPembayaran);
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp?page=payments");
        rd.forward(request, response);
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
        return "Menangani tampilan awal pembayaran";
    }
}
