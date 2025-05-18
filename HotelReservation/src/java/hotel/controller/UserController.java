package hotel.controller;

import hotel.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("errorMsg", "Email dan password wajib diisi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                return;
            }

            try {
                User user = User.login(email, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", user.getIdUser());
                    session.setAttribute("userName", user.getNama());
                    session.setAttribute("userEmail", user.getEmail());
                    session.setAttribute("isAdmin", user instanceof Admin);
                    session.setAttribute("userCreatedAt", user.getCreatedAt());
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
            User.logout(session);
            response.sendRedirect("index.jsp?page=accounts");

        } else {
            // Aksi default jika tidak ada action yang cocok
            response.sendRedirect("index.jsp?page=accounts");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Panggil central handler
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Panggil central handler
    }

    @Override
    public String getServletInfo() {
        return "UserController untuk login, register, dan logout.";
    }
}
