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
        // Teruskan ke view
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp?page=admin");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Selalu jalankan lihatCustomer()
        try {
            Admin admin = new Admin("Admin", "admin@mail.com", "admin123");
            List<Customer> daftarCustomer = admin.lihatCustomer();
            request.setAttribute("daftarCustomer", daftarCustomer);
        } catch (Exception e) {
            request.setAttribute("errorMsg", "Terjadi kesalahan saat melihat data Customer.");
        }

        // Teruskan ke halaman index.jsp?page=admin
        request.getRequestDispatcher("index.jsp?page=admin").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("tambahCustomer".equals(action)) {
            String nama = request.getParameter("nama");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            try {
                Customer customer = new Customer(nama, email, password);
                if (customer.register()) {
                    request.getSession().setAttribute("successMsg", "Registrasi dari Admin berhasil.");
                } else {
                    request.setAttribute("errorMsg", "Registrasi gagal, email sudah terdaftar.");
                }

            } catch (SQLIntegrityConstraintViolationException e) {
                request.setAttribute("errorMsg", "Email sudah terdaftar. Silakan gunakan email lain.");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "Terjadi kesalahan saat registrasi. Silakan coba lagi.");
            }
            try {
                Admin admin = new Admin("Admin", "admin@mail.com", "admin123");
                List<Customer> daftarCustomer = admin.lihatCustomer();
                request.setAttribute("daftarCustomer", daftarCustomer);
            } catch (Exception e) {
                request.setAttribute("errorMsg", "Terjadi kesalahan saat melihat data Customer.");
            }
            request.getRequestDispatcher("index.jsp?page=admin").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
