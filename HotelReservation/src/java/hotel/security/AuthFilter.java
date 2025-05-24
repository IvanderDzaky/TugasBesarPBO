package hotel.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {
    "/Dashboard",
    "/Admins",
    "/Rooms/CheckAvailability/Reserve"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // false: jangan buat session baru

        String token = (session != null) ? (String) session.getAttribute("auth_token") : null;

        if (token == null || !JWTUtil.isValid(token)) {
            if (session != null) {
                session.setAttribute("errorMsg", "Silakan login terlebih dahulu.");
            }
            res.sendRedirect(req.getContextPath() + "/Accounts");
            return;
        }

        // Cek hak akses berdasarkan path
        String uri = req.getRequestURI();
        boolean isAdmin = JWTUtil.isAdmin(token);

        if (uri.contains("/Admins") && !isAdmin) {
            session.setAttribute("errorMsg", "Akses hanya untuk Admin.");
            res.sendRedirect(req.getContextPath() + "/Accounts");
            return;
        }

        if (uri.contains("/Reserve") && isAdmin) {
            session.setAttribute("errorMsg", "Akses hanya untuk Customer.");
            res.sendRedirect(req.getContextPath() + "/Accounts");
            return;
        }

        // Jika semua lolos, lanjutkan request
        chain.doFilter(request, response);
    }
}
