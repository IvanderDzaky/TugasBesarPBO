package hotel.controller;

import hotel.helper.Bayar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ConfirmPayment", urlPatterns = {"/Payment/ConfirmPayment"})
public class ConfirmPayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("idReservasi");
        String metode = request.getParameter("metode");
        String jumlahBayarParam = request.getParameter("jumlahBayar");

        if (idParam == null || metode == null || jumlahBayarParam == null ||
            idParam.isEmpty() || metode.isEmpty() || jumlahBayarParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idParam + "&error=missing");
            return;
        }

        try {
            int idReservasi = Integer.parseInt(idParam);
            double jumlahBayar = Double.parseDouble(jumlahBayarParam);

            boolean sukses = Bayar.simpanPembayaran(idReservasi, metode, jumlahBayar);

            if (sukses) {
                // Redirect ke tampilan pembayaran dengan countdown
                response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idReservasi + "&fromConfirm=true");
            } else {
                // Jika gagal menyimpan, redirect kembali ke halaman dengan pesan error
                response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idReservasi + "&error=simpan");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idParam + "&error=format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idParam + "&error=exception");
        }
    }
}
