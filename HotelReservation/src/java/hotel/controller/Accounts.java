package hotel.controller;

import hotel.model.Admin;
import hotel.model.Customer;
import hotel.model.User;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Accounts", urlPatterns = {"/Accounts"})
public class Accounts extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            return;
        }

        switch (action) {
            case "login":
                handleLogin(request, response);
                break;

            case "logout":
                handleLogout(request, response);
                break;

            case "register":
                handleRegister(request, response);
                break;

            default:
                response.sendRedirect("index.jsp?page=accounts");
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.getSession().setAttribute("errorMsg", "Email dan password wajib diisi.");
            response.sendRedirect("Accounts");
            return;
        }

        try {
            User user = User.login(email, password);
            if (user != null) {
                user.info(request);
                request.getSession().setAttribute("successMsg", "Selamat datang, " + user.getNama());
                response.sendRedirect("Home");
                return;
            } else {
                request.getSession().setAttribute("errorMsg", "Email atau password salah.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan saat login.");

        }
        response.sendRedirect("Admins");
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        User.logout(session);
        response.sendRedirect("Accounts");
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nama = request.getParameter("nama");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (nama == null || email == null || password == null
                || nama.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.getSession().setAttribute("errorMsg", "Nama, email, dan password wajib diisi.");
            response.sendRedirect("Accounts");
            return;
        }

        try {
            Customer customer = new Customer(nama, email, password);
            if (customer.register()) {
                request.getSession().setAttribute("successMsg", "Registrasi berhasil! Silakan login.");
            } else {
                request.getSession().setAttribute("errorMsg", "Registrasi gagal, email sudah terdaftar.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            request.getSession().setAttribute("errorMsg", "Email sudah terdaftar. Silakan gunakan email lain.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Terjadi kesalahan saat registrasi. Silakan coba lagi.");
        }
        response.sendRedirect("Accounts");
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
        return "Handles login, logout, and registration for users";
    }
}
