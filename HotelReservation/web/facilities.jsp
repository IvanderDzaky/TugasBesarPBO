<%@ page import="java.util.*" %>
<%@ page import="hotel.helper.*" %>
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Facilities</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="Home">Home</a></li>
                    <li>&bullet;</li>
                    <li>Facilities</li>
                </ul>
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

<section class="section contact-section" id="next">
    <div class="container">
        <% List<Fasilitas> daftarFasilitas = (List<Fasilitas>) request.getAttribute("daftarFasilitas"); %>


        <div class="row justify-content-center">
            <%
                if (daftarFasilitas != null) {
                    for (Fasilitas f : daftarFasilitas) {
            %>
            <div class="col-md-4" data-aos="fade-up" data-aos-delay="100">

                <div class="big-white rounded shadow p-4 border-top border-4 border-dark text-center">
                    <img src="images/<%= f.getNamaFasilitas()%>.jpg" width="50px" class="mb-3">
                    <h5><%= f.getNamaFasilitas()%></h5>
                    <p>High-speed internet access available in all rooms and public areas.</p>
                </div>
            </div>
        </div>
        <%  }
            }%>
    </div>
</section>

<section class="section testimonial-section bg-light">
    <div class="container">
        <div class="row justify-content-center text-center mb-5">
            <div class="col-md-7">
                <h2 class="heading" data-aos="fade-up">People Says</h2>
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
