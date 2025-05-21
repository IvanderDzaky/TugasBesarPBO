<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Grizz Hotel</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="" />
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/https://fonts.googleapis.com/css?family=|Roboto+Sans:400,700|Playfair+Display:400,700">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aos.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.timepicker.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fancybox.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/ionicons/css/ionicons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome/css/font-awesome.min.css">


        <!-- Theme Style -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    </head>
    <body>
        <jsp:include page="header-footer/header.jsp" />
        <%-- Toast Notification Container --%>
        <div id="toast" style="display:none; position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background-color: #f44336; color: white; padding: 16px 24px; border-radius: 8px; z-index: 1000; box-shadow: 0 2px 6px rgba(0,0,0,0.3); text-align: center;">
            <span id="toast-msg"></span>
        </div>
        <script>
            function showToast(message, isSuccess = false) {
                const toast = document.getElementById("toast");
                const toastMsg = document.getElementById("toast-msg");

                toastMsg.innerText = message;
                toast.style.backgroundColor = isSuccess ? "#4CAF50" : "#f44336"; // hijau / merah
                toast.style.display = "block";

                setTimeout(() => {
                    toast.style.display = "none";
                }, 3000);
            }
        </script>
        <%
            String errorMsg = (String) session.getAttribute("errorMsg");
            String successMsg = (String) session.getAttribute("successMsg");
            if (successMsg != null) {
                session.removeAttribute("successMsg"); // agar tidak tampil lagi di refresh
            }
            if (errorMsg != null) {
        %>

        <script> showToast("<%= errorMsg.replace("\"", "\\\"")%>");</script>
        <%
        } else if (successMsg != null) {
        %>

        <script> showToast("<%= successMsg.replace("\"", "\\\"")%>", true);</script>
        <%
            }
        %>
        <%
            String p = request.getParameter("page");
            if ("home".equals(p) || p == null) {
        %>
        <section class="site-hero overlay" style="background-image: url(images/slider-7.jpg)" data-stellar-background-ratio="0.5">
            <div class="container">
                <div class="row site-hero-inner justify-content-center align-items-center">
                    <div class="col-md-10 text-center" data-aos="fade-up">
                        <span class="custom-caption text-uppercase text-white d-block  mb-3">Welcome To Grizzlane <span class="fa fa-star text-primary"></span>   Hotel Reservation</span>
                        <h1 class="heading">Search A Best Place To Stay</h1>
                    </div>
                </div>
            </div>

            <a class="mouse smoothscroll" href="#next">
                <div class="mouse-icon">
                    <span class="mouse-wheel"></span>
                </div>
            </a>
        </section>
        <!-- END section -->

        <section class="py-5 bg-light">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-12 col-lg-7 ml-auto order-lg-2 position-relative mb-5" data-aos="fade-up" id="next">
                        <figure class="img-absolute">
                            <img src="images/foods_1.jpg" alt="Image" class="img-fluid">
                        </figure>
                        <img src="images/family_room.jpg" alt="Image" class="img-fluid rounded">
                    </div>
                    <div class="col-md-12 col-lg-4 order-lg-1" data-aos="fade-up">
                        <h2 class="heading">Welcome!</h2>
                        <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                        <p><a href="Rooms" class="btn btn-primary text-white py-2 mr-3">Learn More</a> <span class="mr-3 font-family-serif"><em>or</em></span> <a href="https://youtu.be/A3eDzR1BmEo?si=d9Ym43JyEs81oQ20"  data-fancybox class="text-uppercase letter-spacing-1">See video</a></p>
                    </div>

                </div>
            </div>
        </section>

        <section class="section">
            <div class="container">
                <div class="row justify-content-center text-center mb-5">
                    <div class="col-md-7">
                        <h2 class="heading" data-aos="fade-up">Rooms &amp; Suites</h2>
                        <p data-aos="fade-up" data-aos-delay="100">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-lg-4" data-aos="fade-up">
                        <a href="Rooms" class="room">
                            <figure class="img-wrap">
                                <img src="images/single_room.jpg" alt="Free website template" class="img-fluid mb-3">
                            </figure>
                            <div class="p-3 text-center room-info">
                                <h2>Single Room</h2>
                                <span class="text-uppercase letter-spacing-1">90$ / per night</span>
                            </div>
                        </a>
                    </div>

                    <div class="col-md-6 col-lg-4" data-aos="fade-up">
                        <a href="Rooms" class="room">
                            <figure class="img-wrap">
                                <img src="images/img_2.jpg" alt="Free website template" class="img-fluid mb-3">
                            </figure>
                            <div class="p-3 text-center room-info">
                                <h2>Family Room</h2>
                                <span class="text-uppercase letter-spacing-1">120$ / per night</span>
                            </div>
                        </a>
                    </div>

                    <div class="col-md-6 col-lg-4" data-aos="fade-up">
                        <a href="Rooms" class="room">
                            <figure class="img-wrap">
                                <img src="images/img_3.jpg" alt="Free website template" class="img-fluid mb-3">
                            </figure>
                            <div class="p-3 text-center room-info">
                                <h2>Presidential Room</h2>
                                <span class="text-uppercase letter-spacing-1">250$ / per night</span>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="row justify-content-center text-center mb-5" data-aos="fade-up">
                    <a href="Rooms" class="btn btn-sm btn-outline-dark rounded-0 fw-bold shadow-none">More Room >>></a>
                </div>
            </div>
        </section>


        <section class="section slider-section bg-light">
            <div class="container">
                <div class="row justify-content-center text-center mb-5">
                    <div class="col-md-7">
                        <h2 class="heading" data-aos="fade-up">Photos</h2>
                        <p data-aos="fade-up" data-aos-delay="100">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="home-slider major-caousel owl-carousel mb-5" data-aos="fade-up" data-aos-delay="200">
                            <div class="slider-item">
                                <a href="images/presidential_room.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/presidential_room.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                            <div class="slider-item">
                                <a href="images/Convention_hall.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/Convention_hall.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                            <div class="slider-item">
                                <a href="images/img_3.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/img_3.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                            <div class="slider-item">
                                <a href="images/slider-4.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/slider-4.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                            <div class="slider-item">
                                <a href="images/Deluxe_Suite.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/Deluxe_Suite.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                            <div class="slider-item">
                                <a href="images/slider-6.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/slider-6.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                            <div class="slider-item">
                                <a href="images/slider-7.jpg" data-fancybox="images" data-caption="Caption for this image"><img src="images/slider-7.jpg" alt="Image placeholder" class="img-fluid"></a>
                            </div>
                        </div>
                        <!-- END slider -->
                    </div>
                </div>
            </div>
        </section>
        <!-- END section -->

        <section class="section bg-image overlay" style="background-image: url('images/hero_3.jpg');">
            <div class="container">
                <div class="row justify-content-center text-center mb-5">
                    <div class="col-md-7">
                        <h2 class="heading text-white" data-aos="fade">Our Restaurant Menu</h2>
                        <p class="text-white" data-aos="fade" data-aos-delay="100">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                    </div>
                </div>
                <div class="food-menu-tabs" data-aos="fade">
                    <ul class="nav nav-tabs mb-5" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active letter-spacing-2" id="mains-tab" data-toggle="tab" href="#mains" role="tab" aria-controls="mains" aria-selected="true">Mains</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link letter-spacing-2" id="desserts-tab" data-toggle="tab" href="#desserts" role="tab" aria-controls="desserts" aria-selected="false">Desserts</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link letter-spacing-2" id="drinks-tab" data-toggle="tab" href="#drinks" role="tab" aria-controls="drinks" aria-selected="false">Drinks</a>
                        </li>
                    </ul>
                    <div class="tab-content py-5" id="myTabContent">
                        <div class="tab-pane fade show active text-left" id="mains" role="tabpanel" aria-labelledby="mains-tab">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$20.00</span>
                                        <h3 class="text-white">Murgh Tikka Masala</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$35.00</span>
                                        <h3 class="text-white">Fish Moile</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$15.00</span>
                                        <h3 class="text-white">Safed Ghost</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$10.00</span>
                                        <h3 class="text-white">French Toast Combo</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$8.35</span>
                                        <h3 class="text-white">Vegie Omelet</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$22.00</span>
                                        <h3 class="text-white">Chorizo &amp; Egg Omelet</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                </div>
                            </div>


                        </div> <!-- .tab-pane -->

                        <div class="tab-pane fade text-left" id="desserts" role="tabpanel" aria-labelledby="desserts-tab">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$11.00</span>
                                        <h3 class="text-white">Banana Split</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$72.00</span>
                                        <h3 class="text-white">Sticky Toffee Pudding</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$26.00</span>
                                        <h3 class="text-white">Pecan</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$42.00</span>
                                        <h3 class="text-white">Apple Strudel</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$7.35</span>
                                        <h3 class="text-white">Pancakes</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$22.00</span>
                                        <h3 class="text-white">Ice Cream Sundae</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- .tab-pane -->
                        <div class="tab-pane fade text-left" id="drinks" role="tabpanel" aria-labelledby="drinks-tab">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$32.00</span>
                                        <h3 class="text-white">Spring Water</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$14.00</span>
                                        <h3 class="text-white">Coke, Diet Coke, Coke Zero</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$93.00</span>
                                        <h3 class="text-white">Orange Fanta</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$18.00</span>
                                        <h3 class="text-white">Lemonade, Lemon Squash</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$38.35</span>
                                        <h3 class="text-white">Sparkling Mineral Water</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                    <div class="food-menu mb-5">
                                        <span class="d-block text-primary h4 mb-3">$69.00</span>
                                        <h3 class="text-white">Lemon, Lime &amp; Bitters</h3>
                                        <p class="text-white text-opacity-7">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- .tab-pane -->
                    </div>
                </div>
            </div>
        </section>
        <!-- END section -->
        <section class="section testimonial-section">
            <div class="container">
                <div class="row justify-content-center text-center mb-5">
                    <div class="col-md-7">
                        <h2 class="heading" data-aos="fade-up">Testimonial Says</h2>
                    </div>
                </div>
                <div class="row">
                    <div class="js-carousel-2 owl-carousel mb-5" data-aos="fade-up" data-aos-delay="200">

                        <div class="testimonial text-center slider-item">
                            <div class="author-image mb-3">
                                <img src="images/person_1.jpg" alt="Image placeholder" class="rounded-circle mx-auto">
                            </div>
                            <blockquote>

                                <p>&ldquo;A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.&rdquo;</p>
                            </blockquote>
                            <p><em>&mdash; Jean Smith</em></p>
                        </div> 

                        <div class="testimonial text-center slider-item">
                            <div class="author-image mb-3">
                                <img src="images/person_2.jpg" alt="Image placeholder" class="rounded-circle mx-auto">
                            </div>
                            <blockquote>
                                <p>&ldquo;Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.&rdquo;</p>
                            </blockquote>
                            <p><em>&mdash; John Doe</em></p>
                        </div>

                        <div class="testimonial text-center slider-item">
                            <div class="author-image mb-3">
                                <img src="images/person_3.jpg" alt="Image placeholder" class="rounded-circle mx-auto">
                            </div>
                            <blockquote>

                                <p>&ldquo;When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane.&rdquo;</p>
                            </blockquote>
                            <p><em>&mdash; John Doe</em></p>
                        </div>


                        <div class="testimonial text-center slider-item">
                            <div class="author-image mb-3">
                                <img src="images/person_1.jpg" alt="Image placeholder" class="rounded-circle mx-auto">
                            </div>
                            <blockquote>

                                <p>&ldquo;A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.&rdquo;</p>
                            </blockquote>
                            <p><em>&mdash; Jean Smith</em></p>
                        </div> 

                        <div class="testimonial text-center slider-item">
                            <div class="author-image mb-3">
                                <img src="images/person_2.jpg" alt="Image placeholder" class="rounded-circle mx-auto">
                            </div>
                            <blockquote>
                                <p>&ldquo;Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.&rdquo;</p>
                            </blockquote>
                            <p><em>&mdash; John Doe</em></p>
                        </div>

                        <div class="testimonial text-center slider-item">
                            <div class="author-image mb-3">
                                <img src="images/person_3.jpg" alt="Image placeholder" class="rounded-circle mx-auto">
                            </div>
                            <blockquote>

                                <p>&ldquo;When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane.&rdquo;</p>
                            </blockquote>
                            <p><em>&mdash; John Doe</em></p>
                        </div>

                    </div>
                    <!-- END slider -->
                </div>

            </div>
        </section>


        <section class="section blog-post-entry bg-light">
            <div class="container">
                <div class="row justify-content-center text-center mb-5">
                    <div class="col-md-7">
                        <h2 class="heading" data-aos="fade-up">Events</h2>
                        <p data-aos="fade-up">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4 col-md-6 col-sm-6 col-12 post" data-aos="fade-up" data-aos-delay="100">

                        <div class="media media-custom d-block mb-4 h-100">
                            <img src="images/single_room.jpg" alt="Image placeholder" class="img-fluid">
                            <div class="media-body">
                                <span class="meta-post">February 26, 2018</span>
                                <h2 class="mt-0 mb-3"><a href="#">Travel Hacks to Make Your Flight More Comfortable</a></h2>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                            </div>
                        </div>

                    </div>
                    <div class="col-lg-4 col-md-6 col-sm-6 col-12 post" data-aos="fade-up" data-aos-delay="200">
                        <div class="media media-custom d-block mb-4 h-100">
                            <img src="images/img_2.jpg" alt="Image placeholder" class="img-fluid">
                            <div class="media-body">
                                <span class="meta-post">February 26, 2018</span>
                                <h2 class="mt-0 mb-3"><a href="#">5 Job Types That Aallow You To Earn As You Travel The World</a></h2>
                                <p>Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6 col-sm-6 col-12 post" data-aos="fade-up" data-aos-delay="300">
                        <div class="media media-custom d-block mb-4 h-100">
                           <img src="images/img_3.jpg" alt="Image placeholder" class="img-fluid">
                            <div class="media-body">
                                <span class="meta-post">February 26, 2018</span>
                                <h2 class="mt-0 mb-3"><a href="#">30 Great Ideas On Gifts For Travelers</a></h2>
                                <p>A small river named Duden flows by their place and supplies it with the necessary regelialia. t is a paradisematic country, in which roasted parts of sentences.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="section bg-image overlay" style="background-image: url('images/hero_4.jpg');">
            <div class="container" >
                <div class="row align-items-center">
                    <div class="col-12 col-md-6 text-center mb-4 mb-md-0 text-md-left" data-aos="fade-up">
                        <h2 class="text-white font-weight-bold">A Best Place To Stay. Reserve Now!</h2>
                    </div>
                    <div class="col-12 col-md-6 text-center text-md-right" data-aos="fade-up" data-aos-delay="200">
                        <a href="Rooms" class="btn btn-outline-white-primary py-3 text-white px-5">Reserve Now</a>
                    </div>
                </div>
            </div>
        </section>
        <%
        } else if ("accounts".equals(p)) {
        %>
        <jsp:include page="accounts.jsp" flush="false"></jsp:include>
        <%
        } else if ("dashboard".equals(p)) {
        %>
        <jsp:include page="dashboard.jsp" flush="false"></jsp:include>
        <%
        } else if ("admin".equals(p)) {
        %>
        <jsp:include page="admin.jsp" flush="true"></jsp:include>
        <%
        } else if ("about".equals(p)) {
        %>
        <jsp:include page="about.jsp" flush="false"></jsp:include>
        <%
        } else if ("facilities".equals(p)) {
        %>
        <jsp:include page="facilities.jsp" flush="false"></jsp:include>
        <%
        } else if ("rooms".equals(p)) {
        %>
        <jsp:include page="rooms.jsp" flush="false"></jsp:include>
        <% } else {
        %>
        <h2>Halaman tidak ditemukan</h2>
        <%
            }
        %>

        <jsp:include page="header-footer/footer.jsp" />

        <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-migrate-3.0.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.stellar.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.fancybox.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/aos.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script> 
        <script src="${pageContext.request.contextPath}/js/jquery.timepicker.min.js"></script> 
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
        <!-- Global site tag (gtag.js) - Google Analytics -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"></script>
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag() {
                dataLayer.push(arguments);
            }
            gtag('js', new Date());

            gtag('config', 'UA-23581568-13');
            function showToast(message, isSuccess = false) {
                const toast = document.getElementById("toast");
                const toastMsg = document.getElementById("toast-msg");

                toastMsg.innerText = message;
                toast.style.backgroundColor = isSuccess ? "#4CAF50" : "#f44336"; // hijau / merah
                toast.style.display = "block";

                setTimeout(() => {
                    toast.style.display = "none";
                }, 3000);
            }
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

    </body>
</html>
