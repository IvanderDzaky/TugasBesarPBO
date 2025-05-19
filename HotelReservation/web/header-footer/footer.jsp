<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    HttpSession sess = request.getSession(false);
    boolean loggedIn = (sess != null && sess.getAttribute("userName") != null);
    String userEmail = loggedIn ? (String) sess.getAttribute("userEmail") : "";
    Timestamp createdAt = loggedIn ? (Timestamp) sess.getAttribute("userCreatedAt") : null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
    String userCreatedAtStr = (createdAt != null) ? sdf.format(createdAt) : "-";
    
    request.setAttribute("userCreatedAt", userCreatedAtStr); 
    request.setAttribute("userEmail", userEmail); 
%>
<footer class="section footer-section">
    <div class="container">
        <div class="row mb-4">
            <div class="col-md-3 mb-5">
                <ul class="list-unstyled link">
                    <li><a href="About">About Us</a></li>
                    <li><a href="#">Terms &amp; Conditions</a></li>
                    <li><a href="#">Privacy Policy</a></li>
                    <li><a href="Rooms">Rooms</a></li>
                </ul>
            </div>
            <div class="col-md-3 mb-5">
                <ul class="list-unstyled link">
                    <li><a href="Rooms">The Rooms &amp; Suites</a></li>
                    <li><a href="About">About Us</a></li>
                    <li><a href="About">Contact Us</a></li>
                    <li><a href="Home#mains">Restaurant</a></li>
                </ul>
            </div>
            <div class="col-md-3 mb-5 pr-md-5 contact-info">
                <!-- <li>198 West 21th Street, <br> Suite 721 New York NY 10016</li> -->
                <p><span class="d-block"><span class="ion-ios-location h5 mr-3 text-primary"></span>Address:</span> <span> JL Telkom Mabar <br> Hotel Telkom TULT</span></p>
                <p><span class="d-block"><span class="ion-ios-telephone h5 mr-3 text-primary"></span>Phone:</span> <span> (+62)8179187676</span></p>
                <p><span class="d-block"><span class="ion-ios-email h5 mr-3 text-primary"></span>Email:</span> <span> SogoHotel@gmail.com</a></span></p>
            </div>
            <div class="col-md-3 mb-5">
                <% if (!loggedIn) {%>
                <p>Sign up for our newsletter</p>
                <form onsubmit="location.href = 'Accounts'; return false;" class="footer-newsletter">
                    <div class="form-group">
                        <input type="email" class="form-control" placeholder="Email...">
                        <button type="submit" class="btn"><span class="fa fa-paper-plane"></span></button>
                    </div>
                </form>
                <% } else {%>
                <form class="footer-newsletter">
                    <div class="form-group">
                        <p>
                            <span class="d-block">
                                <span class="ion-ios-email h5 mr-3 text-primary"></span>Email:
                            </span>
                            <span> ${userEmail}</a></span>
                        </p>
                        <p>
                            <span class="d-block">
                                <span class="ion-ios-calendar h5 mr-3 text-primary"></span>Akun Dibuat:
                            </span> 
                            <span>${userCreatedAt}</span>
                        </p>
                    </div>
                </form>
                <% }%>
            </div>
        </div>
    </div>
</footer>

