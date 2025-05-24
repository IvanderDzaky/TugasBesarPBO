package hotel.controller;

import hotel.model.*;
import hotel.security.JWTUtil;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;

@WebServlet(name = "Accounts", urlPatterns = {"/Accounts"})
public class Accounts extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            HttpSession session = request.getSession();
            String csrfToken = UUID.randomUUID().toString();
            session.setAttribute("csrf_token", csrfToken);
            request.setAttribute("csrf_token", csrfToken);
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
                response.sendRedirect("Accounts");
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Validasi CSRF token
        String sessionToken = (String) session.getAttribute("csrf_token");
        String formToken = request.getParameter("csrf_token");

        if (sessionToken == null || formToken == null || !sessionToken.equals(formToken)) {
            session.setAttribute("errorMsg", "CSRF Token SALAH.");
            response.sendRedirect("Accounts");
            return;
        }

        // Hapus token setelah dipakai (opsional, untuk satu kali pakai)
        session.removeAttribute("csrf_token");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            session.setAttribute("errorMsg", "Email dan password wajib diisi.");
            response.sendRedirect("Accounts");
            return;
        }

        try {
            User user = User.login(email, password);
            if (user != null) {
                user.info(request);
                String token = JWTUtil.generateToken(user.getIdUser(), user.getIsAdmin(), user.getEmail());
                session.setAttribute("user", user);
                session.setAttribute("auth_token", token);
                session.setAttribute("successMsg", "Selamat datang, " + user.getNama());
                response.sendRedirect("Home");
                return;
            } else {
                session.setAttribute("errorMsg", "Email atau password salah.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Terjadi kesalahan saat login.");
        }

        response.sendRedirect("Accounts");
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
