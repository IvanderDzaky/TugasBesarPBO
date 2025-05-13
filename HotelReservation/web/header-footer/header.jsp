<%
    HttpSession sess = request.getSession(false);
    boolean loggedIn = (sess != null && sess.getAttribute("userName") != null);
    String userName = loggedIn ? (String) sess.getAttribute("userName") : "";
%>
<header class="site-header js-site-header">
    <div class="container-fluid">
        <div class="row align-items-center">
            <div class="col-6 col-lg-4 site-logo" data-aos="fade">
                <a href="index.jsp?page=home">HOME</a>
                <% if (loggedIn) {%>
                <div class="username-welcome mt-1">
                    <strong><%= userName%></strong>
                </div>
                <% }%>
            </div>
            <div class="col-6 col-lg-8">
                <div class="site-menu-toggle js-site-menu-toggle"  data-aos="fade">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </div>
    </div>
</header>

