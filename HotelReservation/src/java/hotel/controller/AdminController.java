package hotel.controller;

import hotel.model.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("lihatCustomer".equals(action) || action == null) {
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

            // Tampilkan kembali data customer setelah penambahan
            try {
                Admin admin = new Admin("Admin", "admin@mail.com", "admin123");
                List<Customer> daftarCustomer = admin.lihatCustomer();
                request.setAttribute("daftarCustomer", daftarCustomer);
            } catch (Exception e) {
                request.setAttribute("errorMsg", "Gagal memuat data customer setelah registrasi.");
            }

            request.getRequestDispatcher("index.jsp?page=admin").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
