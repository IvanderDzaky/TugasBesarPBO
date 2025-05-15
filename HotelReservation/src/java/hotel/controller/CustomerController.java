package hotel.controller;

import hotel.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "CustomerController", urlPatterns = {"/CustomerController"})
public class CustomerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Customer Controller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String nama = request.getParameter("nama");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validasi input
            if (nama == null || email == null || password == null || nama.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("errorMsg", "Nama, email, dan password wajib diisi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                return;
            }

            try {
                Customer customer = new Customer(nama, email, password);
                if (customer.register()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("successMsg", "Registrasi berhasil! Silakan login.");
                    response.sendRedirect("index.jsp?page=accounts");
                } else {
                    request.setAttribute("errorMsg", "Registrasi gagal, email sudah terdaftar.");
                    request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                }

            } catch (SQLIntegrityConstraintViolationException e) {
                request.setAttribute("errorMsg", "Email sudah terdaftar. Silakan gunakan email lain.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "Terjadi kesalahan saat registrasi. Silakan coba lagi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp?page=accounts");
    }

    @Override
    public String getServletInfo() {
        return "CustomerController untuk menangani registrasi customer.";
    }
}
