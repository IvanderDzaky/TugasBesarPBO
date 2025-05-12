<%
  if (!"accounts".equals(request.getParameter("page"))) {
    response.sendRedirect("index.jsp?page=accounts");
    return;
  }
%>

<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Events</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp">Home</a></li>
                    <li>&bullet;</li>
                    <li>Accounts</li>
                </ul>
            </div>
        </div>
    </div>

    <a class="mouse smoothscroll" href="index.jsp?page=accounts#next">
        <div class="mouse-icon">
            <span class="mouse-wheel"></span>
        </div>
    </a>
</section>
<section class="section bg-light pb-0"  >
    <div class="container">
        <h1>Login to your account ?</h1>
        <div class="social-login">
            <button class="google">
                <i class='bx bxl-google'></i>
                Use Google
            </button>
            <button class="google">
                <i class='bx bxl-apple'></i>
                Use Apple
            </button>
        </div>
        <div class="divider">
            <div class="line"></div>
            <p>Or</p>
            <div class="line"></div>
        </div>

        <form>
            <label for="email">Email:</label>
            <div class="custome-input">
                <input type="email" name="email" placeholder="Your Email" autocomplete="off">
                <i class='bx bx-at'></i>
            </div>
            <label for="password">Password:</label>
            <div class="custome-input">
                <input type="password" name="password" placeholder="Your Password">
                <i class='bx bx-lock-alt'></i>
            </div>
            <button class="login">Login</button>
            <div class="links">
                <a href="#">Reset Password</a>
                <a href="#">Don't have an account?</a>
            </div>
        </form>

    </div>
</section>