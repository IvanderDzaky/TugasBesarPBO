<!-- section hero -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Accounts</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp?page=home">Home</a></li>
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
<style>
    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap');
    /* ================================
   START: Account Page Styles
   ================================ */

    .account-page {
        background: linear-gradient(to right, #e2e2e2, #c9d6ff);
        font-family: 'Montserrat', sans-serif;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .account-page * {
        box-sizing: border-box;
    }

    .account-page .container {
        background-color: #fff;
        border-radius: 30px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
        position: relative;
        overflow: hidden;
        width: 768px;
        max-width: 100%;
        min-height: 480px;
    }

    .account-page .container p {
        font-size: 14px;
        line-height: 20px;
        letter-spacing: 0.3px;
        margin: 20px 0;
    }

    .account-page .container span {
        font-size: 12px;
    }

    .account-page .container a {
        color: #333;
        font-size: 13px;
        text-decoration: none;
        margin: 15px 0 10px;
    }

    .account-page .container button {
        background-color: #512da8;
        color: #fff;
        font-size: 12px;
        padding: 10px 45px;
        border: 1px solid transparent;
        border-radius: 8px;
        font-weight: 600;
        letter-spacing: 0.5px;
        text-transform: uppercase;
        margin-top: 10px;
        cursor: pointer;
    }

    .account-page .container button.hidden {
        background-color: transparent;
        border-color: #fff;
    }

    .account-page .container form {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 0 40px;
        height: 100%;
    }

    .account-page .container input {
        background-color: #eee;
        border: none;
        margin: 8px 0;
        padding: 10px 15px;
        font-size: 13px;
        border-radius: 8px;
        width: 100%;
        outline: none;
    }

    .account-page .form-container {
        position: absolute;
        top: 0;
        height: 100%;
        transition: all 0.6s ease-in-out;
    }

    .account-page .sign-in {
        left: 0;
        width: 50%;
        z-index: 2;
    }

    .account-page .container.active .sign-in {
        transform: translateX(100%);
    }

    .account-page .sign-up {
        left: 0;
        width: 50%;
        opacity: 0;
        z-index: 1;
    }

    .account-page .container.active .sign-up {
        transform: translateX(100%);
        opacity: 1;
        z-index: 5;
        animation: account-move 0.6s;
    }

    @keyframes account-move {
        0%, 49.99% {
            opacity: 0;
            z-index: 1;
        }
        50%, 100% {
            opacity: 1;
            z-index: 5;
        }
    }

    .account-page .social-icons {
        margin: 20px 0;
    }

    .account-page .social-icons a {
        border: 1px solid #ccc;
        border-radius: 20%;
        display: inline-flex;
        justify-content: center;
        align-items: center;
        margin: 0 3px;
        width: 40px;
        height: 40px;
    }

    .account-page .toggle-container {
        position: absolute;
        top: 0;
        left: 50%;
        width: 50%;
        height: 100%;
        overflow: hidden;
        transition: all 0.6s ease-in-out;
        border-radius: 150px 0 0 100px;
        z-index: 1000;
    }

    .account-page .container.active .toggle-container {
        transform: translateX(-100%);
        border-radius: 0 150px 100px 0;
    }

    .account-page .toggle {
        background: linear-gradient(to right, #5c6bc0, #512da8);
        color: #fff;
        position: relative;
        left: -100%;
        height: 100%;
        width: 200%;
        transform: translateX(0);
        transition: all 0.6s ease-in-out;
    }

    .account-page .container.active .toggle {
        transform: translateX(50%);
    }

    .account-page .toggle-panel {
        position: absolute;
        width: 50%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 0 30px;
        text-align: center;
        top: 0;
        transform: translateX(0);
        transition: all 0.6s ease-in-out;
    }

    .account-page .toggle-left {
        transform: translateX(-200%);
    }

    .account-page .container.active .toggle-left {
        transform: translateX(0);
    }

    .account-page .toggle-right {
        right: 0;
        transform: translateX(0);
    }

    .account-page .container.active .toggle-right {
        transform: translateX(200%);
    }

    /* ================================
       END: Account Page Styles
       ================================ */
</style>
<div class="account-page">
    <div class="container active" id="account-container">

        <!-- Sign Up -->
        <div class="form-container sign-up">
            <form>
                <h1>Create Account</h1>
                <div class="social-icons">
                    <a href="#" class="icon"><i class="fa-brands fa-google-plus-g"></i></a>
                    <a href="#" class="icon"><i class="fa-brands fa-facebook-f"></i></a>
                    <a href="#" class="icon"><i class="fa-brands fa-github"></i></a>
                    <a href="#" class="icon"><i class="fa-brands fa-linkedin-in"></i></a>
                </div>
                <span>or use your email for registration</span>
                <input type="text" placeholder="Name">
                <input type="email" placeholder="Email">
                <input type="password" placeholder="Password">
                <button>Sign Up</button>
            </form>
        </div>

        <!-- Sign In -->
        <div class="form-container sign-in">
            <form>
                <h1>Sign In</h1>
                <div class="social-icons">
                    <a href="#" class="icon"><i class="fa-brands fa-google-plus-g"></i></a>
                    <a href="#" class="icon"><i class="fa-brands fa-facebook-f"></i></a>
                    <a href="#" class="icon"><i class="fa-brands fa-github"></i></a>
                    <a href="#" class="icon"><i class="fa-brands fa-linkedin-in"></i></a>
                </div>
                <span>or use your email password</span>
                <input type="email" placeholder="Email">
                <input type="password" placeholder="Password">
                <a href="#">Forget Your Password?</a>
                <button>Sign In</button>
            </form>
        </div>

        <!-- Toggle -->
        <div class="toggle-container">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    <h1>Welcome Back!</h1>
                    <p>Enter your personal details to use all of site features</p>
                    <button class="hidden" id="login">Sign In</button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1>Hello, Friend!</h1>
                    <p>Register with your personal details to use all of site features</p>
                    <button class="hidden" id="register">Sign Up</button>
                </div>
            </div>
        </div>

    </div>
</div>


<!-- Script untuk toggle login/register -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const container = document.getElementById("account-container");
        const registerBtn = document.getElementById("register");
        const loginBtn = document.getElementById("login");

        if (container && registerBtn && loginBtn) {
            registerBtn.addEventListener("click", () => {
                container.classList.add("active");
            });

            loginBtn.addEventListener("click", () => {
                container.classList.remove("active");
            });
        }
    });
</script>
