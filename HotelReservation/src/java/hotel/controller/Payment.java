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

@WebServlet(name = "Payment", urlPatterns = {"/Payment/*"})
public class Payment extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Payment.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                lihatDataPembayaran(request, response);
                // ambil dari session & pindahkan ke request
                HttpSession session = request.getSession();
                Object showCountdown = session.getAttribute("showCountdown");
                Object idPembayaran = session.getAttribute("idPembayaran");
                Object deadlineMillis = session.getAttribute("deadlineMillis");

                if (showCountdown != null) {
                    request.setAttribute("showCountdown", showCountdown);
                    request.setAttribute("idPembayaran", idPembayaran);
                    request.setAttribute("deadlineMillis", deadlineMillis);

                    // Hapus dari session agar tidak muncul terus
                    session.removeAttribute("showCountdown");
                    session.removeAttribute("idPembayaran");
                    session.removeAttribute("deadlineMillis");
                }
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
            if (idParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter idReservasi diperlukan.");
                return;
            }

            int idReservasi = Integer.parseInt(idParam);
            Reservasi reservasi = Reservasi.getById(idReservasi);
            if (reservasi == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reservasi tidak ditemukan.");
                return;
            }

            int durasi = reservasi.hitungDurasi();
            double totalHarga = reservasi.hitungTotal();

            request.setAttribute("reservasi", reservasi);
            request.setAttribute("kamar", reservasi.getKamar());
            request.setAttribute("durasi", durasi);
            request.setAttribute("totalHarga", totalHarga);
            String statusPembayaran = Bayar.getStatusPembayaranByReservasi(idReservasi);
            request.setAttribute("statusPembayaran", statusPembayaran);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID reservasi tidak valid.");
        }
    }

    protected void handleBayarSekarang(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idReservasi = Integer.parseInt(request.getParameter("idReservasi"));
            String metode = request.getParameter("metode");

            if (metode == null || metode.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Metode pembayaran wajib dipilih.");
                return;
            }

            Reservasi reservasi = Reservasi.getById(idReservasi);
            if (reservasi == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reservasi tidak ditemukan.");
                return;
            }

            double jumlahBayar = reservasi.hitungTotal();
            if (jumlahBayar <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Jumlah bayar tidak valid.");
                return;
            }

            String idPembayaran = "PAY" + System.currentTimeMillis();
            Timestamp deadline = new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

            Pembayaran pembayaran = new Pembayaran(
                    idReservasi,
                    idPembayaran,
                    metode,
                    jumlahBayar,
                    "Pending",
                    null,
                    deadline
            );

            boolean sukses = Bayar.simpanPembayaran(pembayaran);

            if (sukses) {
                // Redirect agar JSP dapat mengambil ulang datanya
                HttpSession session = request.getSession();
                session.setAttribute("showCountdown", true);
                session.setAttribute("idPembayaran", idPembayaran);
                session.setAttribute("deadlineMillis", deadline.getTime());
                request.getSession().setAttribute("successMsg", "Berhasil membuat pembayaran, mohon bayar 1x24 jam");
                response.sendRedirect(request.getContextPath() + "/Payment?idReservasi=" + idReservasi);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Gagal menyimpan pembayaran.");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID reservasi tidak valid.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saat melakukan pembayaran", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Terjadi kesalahan saat memproses pembayaran.");
        }
    }

    protected void handleKonfirmasiPembayaran(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idPembayaran = request.getParameter("idPembayaran");

            if (idPembayaran == null || idPembayaran.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID pembayaran tidak valid.");
                return;
            }

            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setIdPembayaran(idPembayaran);
            pembayaran.setStatus("Lunas");
            pembayaran.setTanggalBayar(new Timestamp(System.currentTimeMillis()));

            boolean berhasil = Bayar.konfirmasiPembayaran(pembayaran);

            if (berhasil) {
                request.getSession().setAttribute("succesMsg", "Pembayaran berhasil dikonfirmasi.");
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Gagal mengonfirmasi pembayaran.");
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saat konfirmasi pembayaran", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Terjadi kesalahan saat konfirmasi.");
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
