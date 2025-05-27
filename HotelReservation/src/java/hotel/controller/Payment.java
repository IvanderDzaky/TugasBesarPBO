package hotel.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import hotel.model.Pembayaran;
import hotel.model.Reservasi;
import hotel.helper.Bayar;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@WebServlet(name = "Payment", urlPatterns = {"/Payment/*"})
public class Payment extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Payment.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                lihatDataPembayaran(request, response);
                request.getRequestDispatcher("index.jsp?page=payments").forward(request, response);
                return;
            } else {
                switch (path) {
                    case "/Bayar":
                        handleBayarSekarang(request, response);
                        break;
                    case "/Konfirmasi":
                        handleKonfirmasiPembayaran(request, response);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in processRequest", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Terjadi kesalahan pada server.");
        }
    }

    protected void lihatDataPembayaran(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("idReservasi");
            int idReservasi = Integer.parseInt(idParam);

            Reservasi reservasi = Reservasi.getById(idReservasi);
            int durasi = reservasi.hitungDurasi();
            double totalHarga = reservasi.hitungTotal();

            Pembayaran pembayaran = Bayar.getByReservasi(idReservasi);

            request.setAttribute("reservasi", reservasi);
            request.setAttribute("kamar", reservasi.getKamar());
            request.setAttribute("pembayaran", pembayaran);
            request.setAttribute("durasi", durasi);
            request.setAttribute("totalHarga", totalHarga);

            if (pembayaran != null && pembayaran.getDeadLine() != null) {
                long deadlineMillis = pembayaran.getDeadLine().getTime();
                request.setAttribute("deadlineMillis", deadlineMillis);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID reservasi tidak valid.");
        }
    }

    protected void handleBayarSekarang(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("idReservasi");

        try {
            int idReservasi = Integer.parseInt(idParam);
            String metode = request.getParameter("metode");

            // Ambil data reservasi
            Reservasi reservasi = Reservasi.getById(idReservasi);

            // Hitung total bayar
            double totalBayar = reservasi.hitungTotal();

            // Generate ID Pembayaran
            String idPembayaran = "PAY-" + System.currentTimeMillis();

            // Deadline 24 jam dari sekarang
            Timestamp deadline = Timestamp.valueOf(LocalDateTime.now().plusHours(24));

            // Buat dan isi objek pembayaran
            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setIdPembayaran(idPembayaran);
            pembayaran.setIdReservasi(idReservasi);
            pembayaran.setMetode(metode);
            pembayaran.setJumlahBayar(totalBayar);
            pembayaran.setStatus("Pending");
            pembayaran.setDeadLine(deadline);
            long deadlineMillis = pembayaran.getDeadLine().getTime();
            request.setAttribute("deadlineMillis", deadlineMillis);

            // Simpan pembayaran
            boolean sukses = Bayar.simpanPembayaran(pembayaran);

            if (sukses) {
                request.getSession().setAttribute("successMsg", "Berhasil membuat pembayaran, mohon bayar dalam 1x24 jam.");
            } else {
                request.getSession().setAttribute("errorMsg", "Gagal menyimpan data pembayaran.");
            }

            response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idReservasi);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMsg", "ID reservasi tidak valid.");
            response.sendRedirect(request.getContextPath() + "/Payment");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Terjadi kesalahan saat memproses pembayaran.");
            response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idParam);
        }
    }

    protected void handleKonfirmasiPembayaran(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
