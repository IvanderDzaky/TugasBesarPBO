<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%
    HttpSession sess = request.getSession(false);
    boolean loggedIn = (sess != null && sess.getAttribute("userId") != null);
    boolean isAdmin = false;
    String userName = loggedIn ? (String) sess.getAttribute("userName") : "";
    if (loggedIn && sess.getAttribute("isAdmin") != null) {
        isAdmin = (Boolean) sess.getAttribute("isAdmin");
    }
    request.setAttribute("userName", userName);
%>       
<header class="site-header js-site-header">
    <jsp:include page="/notification.jsp" />
    <div class="container-fluid">
        <div class="row align-items-center">
            <div class="col-6 col-lg-4 site-logo" data-aos="fade">
                <a href="${pageContext.request.contextPath}/Home">HOME</a>
                <% if (loggedIn) {%>
                <div class="username-welcome mt-1">
                    <strong>${userName}</strong>
                </div>
                <% }%>
            </div>
            <div class="col-6 col-lg-8">


                <div class="site-menu-toggle js-site-menu-toggle"  data-aos="fade">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <!-- END menu-toggle -->

                <div class="site-navbar js-site-navbar">
                    <nav role="navigation">
                        <div class="container">
                            <div class="row full-height align-items-center">
                                <div class="col-md-6 mx-auto">
                                    <ul class="list-unstyled menu">
                                        <li class="<%= "home".equals(request.getParameter("page")) || request.getParameter("page") == null ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/Home">Home</a>
                                        </li>
                                        <% if (!loggedIn) {%>
                                        <li class="<%= "accounts".equals(request.getParameter("page")) ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/Accounts">Accounts</a>
                                        </li>
                                        <% } else if (!isAdmin) {%>
                                        <li class="<%= "dashboard".equals(request.getParameter("page")) ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/Dashboard">Dashboard</a>
                                        </li>
                                        <% } else {%>
                                        <li class="<%= "admin".equals(request.getParameter("page")) ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/Admins">Admins</a>
                                        </li>
                                        <% }%>
                                        <li class="<%= "rooms".equals(request.getParameter("page")) ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/Rooms">Rooms</a>
                                        </li>
                                        <li class="<%= "facilities".equals(request.getParameter("page")) ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/Facilities">Facilities</a>
                                        </li>
                                        <li class="<%= "about".equals(request.getParameter("page")) ? "active" : ""%>">
                                            <a href="${pageContext.request.contextPath}/About">About</a>
                                        </li>
                                        <% if (loggedIn) { %>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/Accounts?action=logout">Logout</a>
                                        </li>
                                        <% }%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</header>