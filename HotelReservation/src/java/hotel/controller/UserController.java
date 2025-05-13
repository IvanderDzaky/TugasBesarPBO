package hotel.controller;

import hotel.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String nama = request.getParameter("nama");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (nama == null || email == null || password == null
                    || nama.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("errorMsg", "Semua kolom wajib diisi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                return;
            }

            User newUser = new User(nama, email, password, false);
            try {
                boolean registered = newUser.register();
                if (registered) {
                    request.setAttribute("successMsg", "Pendaftaran berhasil. Silakan login.");
                } else {
                    request.setAttribute("errorMsg", "Pendaftaran gagal.");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                request.setAttribute("errorMsg", "Email sudah digunakan.");
            } catch (Exception e) {
                request.setAttribute("errorMsg", "Terjadi kesalahan saat registrasi.");
            }

            request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);

        } else if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || password == null
                    || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("errorMsg", "Email dan password wajib diisi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                return;
            }

            try {
                User user = User.login(email, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("userName", user.getNama());
                    session.setAttribute("isAdmin", user.isAdmin());
                    session.setAttribute("successMsg", "Selamat datang, " + user.getNama());
                    response.sendRedirect("index.jsp?page=home");
                } else {
                    request.setAttribute("errorMsg", "Email atau password salah.");
                    request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMsg", "Terjadi kesalahan saat login.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            }
        } else if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                new User().logout(session);
            }
            response.sendRedirect("index.jsp?page=accounts");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        response.sendRedirect("index.jsp?page=accounts");
    }

    @Override
    public String getServletInfo() {
        return "UserController bertugas mengatur login, register, dan logout.";
    }
}
