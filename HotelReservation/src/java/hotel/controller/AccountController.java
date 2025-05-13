package hotel.controller;

import hotel.config.SqlConnect;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet(name = "AccountController", urlPatterns = {"/AccountController"})
public class AccountController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action"); // bisa "login" atau "register"

        if ("register".equals(action)) {
            // === SIGN UP ===
            String nama = request.getParameter("nama");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validasi form kosong
            if (nama == null || nama.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {

                request.setAttribute("errorMsg", "Semua kolom harus diisi untuk registrasi!");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                return;
            }

            try (Connection conn = SqlConnect.getConnection()) {
                String insertSql = "INSERT INTO users (nama, email, password, isAdmin) VALUES (?, ?, ?, false)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, nama);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password); // Hash disarankan
                insertStmt.executeUpdate();

                request.setAttribute("successMsg", "Berhasil mendaftar. Silakan login.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);

            } catch (SQLIntegrityConstraintViolationException e) {
                request.setAttribute("errorMsg", "Email sudah terdaftar!");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("errorMsg", "Terjadi kesalahan saat register. Silakan coba lagi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            }

        } else if ("login".equals(action)) {
            // === SIGN IN ===
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validasi form kosong
            if (email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {

                request.setAttribute("errorMsg", "Email dan password tidak boleh kosong!");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                return;
            }

            try (Connection conn = SqlConnect.getConnection()) {
                String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", rs.getInt("id"));
                    session.setAttribute("userName", rs.getString("nama"));
                    session.setAttribute("isAdmin", rs.getBoolean("isAdmin"));
                    session.setAttribute("successMsg", "Berhasil login. Welcome " + rs.getString("nama") + ".");
                    response.sendRedirect("index.jsp?page=home");
                } else {
                    request.setAttribute("errorMsg", "Email atau password salah!");
                    request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
                }

            } catch (SQLException e) {
                request.setAttribute("errorMsg", "Terjadi kesalahan saat login. Silakan coba lagi.");
                request.getRequestDispatcher("index.jsp?page=accounts").forward(request, response);
            }

        } else if ("logout".equals(action)) {
            // === LOGOUT ===
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("index.jsp?page=accounts");

        } else {
            response.sendRedirect("index.jsp?page=home");
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
            response.sendRedirect("index.jsp?page=accounts");
        } else {
            response.sendRedirect("index.jsp?page=accounts");
        }
    }

    @Override
    public String getServletInfo() {
        return "AccountController handles user login and registration.";
    }
}
