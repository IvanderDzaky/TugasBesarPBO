package hotel.controller;

import hotel.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "Admins", urlPatterns = {"/Admins"})
public class Admins extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Admin admin = new Admin("Admin", "admin@mail.com", "admin123");

            String action = request.getParameter("action");

            if (action == null) {
                tampilkanDaftarCustomer(admin, request);
                tampilkanDaftarKamar(admin, request);
                tampilkanDaftarFasilitas(admin, request);
                request.getRequestDispatcher("index.jsp?page=admin").forward(request, response);
                return;
            }

            switch (action) {
                case "hapusCustomer":
                    handleHapusCustomer(admin, request, response);
                    break;

                case "tambahCustomer":
                    handleTambahCustomer(admin, request, response);
                    break;

                case "updateCustomer":
                    handleUpdateCustomer(admin, request, response);
                    break;

                case "tambahFasilitas":
                    handleTambahFasilitas(admin, request, response);
                    break;
                    
                case "hapusFasilitas":
                    handleHapusFasilitas(admin,request,response);
                    break;
                    
                default:
                    request.getSession().setAttribute("errorMsg", "Aksi tidak dikenali.");
                    response.sendRedirect("Admins");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan saat memproses permintaan.");
            request.getRequestDispatcher("index.jsp?page=admin").forward(request, response);
        }
    }

    private void tampilkanDaftarCustomer(Admin admin, HttpServletRequest request)
            throws SQLException {
        List<Customer> daftarCustomer = admin.lihatCustomer();
        request.setAttribute("daftarCustomer", daftarCustomer);
    }

    private void tampilkanDaftarKamar(Admin admin, HttpServletRequest request)
            throws SQLException {
        List<Kamar> daftarKamar = admin.lihatKamar();
        request.setAttribute("daftarKamar", daftarKamar);
    }

    private void tampilkanDaftarFasilitas(Admin admin, HttpServletRequest request)
            throws SQLException {
        List<Fasilitas> daftarFasilitas = admin.lihatFasilitas();
        request.setAttribute("daftarFasilitas", daftarFasilitas);
    }

    private void handleHapusCustomer(Admin admin, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            admin.hapusCustomer(idUser);
            request.getSession().setAttribute("successMsg", "Customer berhasil dihapus.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Gagal menghapus customer.");
        }
        response.sendRedirect("Admins#customer");
    }

    private void handleTambahCustomer(Admin admin, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nama = request.getParameter("nama");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Customer customer = new Customer(nama, email, password);
            admin.tambahCustomer(customer);
            request.getSession().setAttribute("successMsg", "Registrasi dari Admin berhasil.");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.getSession().setAttribute("errorMsg", "Email sudah terdaftar. Silakan gunakan email lain.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Terjadi kesalahan saat registrasi.");
        }

        response.sendRedirect("Admins#customer");
    }

    private void handleUpdateCustomer(Admin admin, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            String nama = request.getParameter("nama");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Customer customerBaru = new Customer(nama, email, password);
            Admin.updateCustomer(idUser, customerBaru);
            request.getSession().setAttribute("successMsg", "Berhasil memperbarui data Customer");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Gagal memperbarui data customer.");
        }

        response.sendRedirect("Admins#customer");
    }

    private void handleTambahFasilitas(Admin admin, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nama = request.getParameter("nama");
        try {
            Fasilitas fasilitas = new Fasilitas(nama);
            admin.tambahFasilitas(fasilitas);
            request.getSession().setAttribute("successMsg", "Berhasil menambahkan fasilitas.");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.getSession().setAttribute("errorMsg", "Fasilitas sudah tersedia.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Terjadi kesalahan saat menambahkan fasilitas.");
        }

        response.sendRedirect("Admins#fasilitas");
    }

    private void handleHapusFasilitas(Admin admin, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int idFasilitas = Integer.parseInt(request.getParameter("idFasilitas"));
            admin.hapusFasilitas(idFasilitas);
            request.getSession().setAttribute("successMsg", "Fasilitas berhasil dihapus.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Gagal menghapus Fasilitas.");
        }
        response.sendRedirect("Admins#fasilitas");
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
        return "Servlet untuk manajemen customer oleh admin.";
    }
}
