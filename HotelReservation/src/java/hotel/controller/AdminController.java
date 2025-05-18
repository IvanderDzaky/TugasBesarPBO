package hotel.controller;

import hotel.model.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            Admin admin = new Admin("Admin", "admin@mail.com", "admin123");

            if ("hapusCustomer".equals(action)) {
                int idUser = Integer.parseInt(request.getParameter("idUser"));
                admin.hapusCustomer(idUser);
                request.getSession().setAttribute("successMsg", "Customer berhasil dihapus.");
                response.sendRedirect("AdminController");
                return;

            } else if ("tambahCustomer".equals(action)) {
                String nama = request.getParameter("nama");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                try {
                    Customer customer = new Customer(nama, email, password);
                    admin.tambahCustomer(customer);
                    request.getSession().setAttribute("successMsg", "Registrasi dari Admin berhasil.");
                } catch (SQLIntegrityConstraintViolationException e) {
                    request.setAttribute("errorMsg", "Email sudah terdaftar. Silakan gunakan email lain.");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMsg", "Terjadi kesalahan saat registrasi.");
                }
            } else if ("updateCustomer".equals(action)) {
                int idUser = Integer.parseInt(request.getParameter("idUser"));
                String nama = request.getParameter("nama");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                Customer customerBaru = new Customer(nama, email, password);
                try {
                    Admin.updateCustomer(idUser, customerBaru);
                    request.getSession().setAttribute("successMsg", "Berhasil memperbarui data Customer");
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMsg", "Gagal memperbarui data customer.");
                }
            }

            // Tampilkan semua customer setelah aksi apapun
            List<Customer> daftarCustomer = admin.lihatCustomer();
            request.setAttribute("daftarCustomer", daftarCustomer);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan saat memproses permintaan.");
        }

        // Forward ke halaman utama admin
        request.getRequestDispatcher("index.jsp?page=admin").forward(request, response);
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
