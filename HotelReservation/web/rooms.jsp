<%@ page import="java.util.*" %>
<%@ page import="hotel.model.*" %>
<%@ page import="hotel.helper.*" %>
<%
    List<Fasilitas> daftarFasilitas = (List<Fasilitas>) request.getAttribute("daftarFasilitas");
    List<KamarTersedia> hasil = (List<KamarTersedia>) request.getAttribute("hasilKamar");

    String errorMsg = (String) session.getAttribute("errorMsg");
    String successMsg = (String) session.getAttribute("successMsg");

    // Remove setelah ditampilkan
    session.removeAttribute("errorMsg");
    session.removeAttribute("successMsg");
%>

<!-- HERO SECTION -->
<section class="site-hero inner-page overlay" style="background-image: url(${pageContext.request.contextPath}/images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Rooms</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="${pageContext.request.contextPath}/Home">Home</a></li>
                    <li>&bullet;</li>
                    <li>Rooms</li>
                </ul>
            </div>
        </div>
    </div>
    <a class="mouse smoothscroll" href="#next">
        <div class="mouse-icon"><span class="mouse-wheel"></span></div>
    </a>
</section>

<!-- FORM CHECK AVAILABILITY -->
<section class="section pb-4">
    <div class="container">
        <div class="row check-availability" id="next">
            <div class="block-32 col-12" data-aos="fade-up" data-aos-offset="-200">
                <form action="${pageContext.request.contextPath}/Rooms/CheckAvailability" method="post">
                    <div class="row align-items-end">
                        <!-- Check In -->
                        <div class="col-md-6 col-lg-2 mb-3">
                            <label class="font-weight-bold text-black">Check In</label>
                            <div class="field-icon-wrap">
                                <div class="icon"><span class="icon-calendar"></span></div>
                                <input type="text" name="checkin" class="form-control" placeholder="Select date">
                            </div>
                        </div>
                        <!-- Check Out -->
                        <div class="col-md-6 col-lg-2 mb-3">
                            <label class="font-weight-bold text-black">Check Out</label>
                            <div class="field-icon-wrap">
                                <div class="icon"><span class="icon-calendar"></span></div>
                                <input type="text" name="checkout" class="form-control" placeholder="Select date">
                            </div>
                        </div>
                        <!-- Adults -->
                        <div class="col-md-6 col-lg-2 mb-3">
                            <label class="font-weight-bold text-black">Adults</label>
                            <select name="adults" class="form-control">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <!-- Children -->
                        <div class="col-md-6 col-lg-2 mb-3">
                            <label class="font-weight-bold text-black">Children</label>
                            <select name="children" class="form-control">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <!-- Facilities -->
                        <div class="col-md-12 col-lg-2 mb-3">
                            <label class="font-weight-bold text-black d-block">Facilities</label>
                            <div class="form-control" style="height:auto; overflow-y:auto; max-height:150px;">
                                <% if (daftarFasilitas != null) {
                                       for (Fasilitas f : daftarFasilitas) { %>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="fasilitas" value="<%= f.getIdFasilitas()%>">
                                        <label class="form-check-label"><%= f.getNamaFasilitas()%></label>
                                    </div>
                                <% }} %>
                            </div>
                        </div>
                        <!-- Button -->
                        <div class="col-md-6 col-lg-2 mb-3 d-flex justify-content-end align-items-end">
                            <button type="submit" class="btn btn-primary btn-block text-white">Check Availability</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<% if (hasil == null) { %>
<!-- DEFAULT ROOMS - hanya tampil jika belum cari kamar -->
<section class="section">
    <div class="container">
        <div class="row">
            <%
                String[] roomNames = {"Single Room", "Family Room", "Presidential Room", "Suite", "VIP Suite", "Deluxe Suite"};
                String[] roomImages = {"single room.jpg", "family room.jpg", "presidential room.jpg", "suite.jpg", "vip suite.jpg", "deluxe suite.jpg"};
                String[] prices = {"90", "120", "250", "300", "350", "400"};

                for (int i = 0; i < roomNames.length; i++) {
            %>
            <div class="col-md-6 col-lg-4 mb-5" data-aos="fade-up">
                <a href="#" class="room">
                    <figure class="img-wrap">
                        <img src="${pageContext.request.contextPath}/images/<%= roomImages[i] %>" alt="room image" class="img-fluid mb-3">
                    </figure>
                    <div class="p-3 text-center room-info">
                        <h2><%= roomNames[i] %></h2>
                        <span class="text-uppercase letter-spacing-1"><%= prices[i] %>$ / per night</span>
                    </div>
                </a>
            </div>
            <% } %>
        </div>
    </div>
</section>
<% } %>

<% if (hasil != null && !hasil.isEmpty()) { %>
<!-- HASIL PENCARIAN KAMAR TERSEDIA -->
<section class="section bg-light" id="kamar-tersedia">
    <div class="container">
        <div class="row justify-content-center text-center mb-5">
            <div class="col-md-7">
                <h2 class="heading" data-aos="fade">Kamar Tersedia</h2>
                <p data-aos="fade">Berikut hasil kamar yang cocok dengan pencarian Anda.</p>
            </div>
        </div>
        <%
            int delay = 100;
            for (KamarTersedia k : hasil) {
        %>
        <div class="site-block-half d-block d-lg-flex bg-white mb-4" data-aos="fade" data-aos-delay="<%= delay %>">
            <a href="#" class="image d-block bg-image-2" style="background-image: url('${pageContext.request.contextPath}/images/<%= k.getTipe().toLowerCase() %>.jpg');"></a>
            <div class="text">
                <span class="d-block mb-4">
                    <span class="display-4 text-primary">$<%= k.getHarga() %></span>
                    <span class="text-uppercase letter-spacing-2">/ per night</span>
                </span>
                <h2 class="mb-3"><%= k.getTipe() %></h2>
                <p><strong>Features:</strong> <%= k.getJumlahTersedia() %> Room available</p>
                <p><strong>Facilities:</strong>
                    <%
                        List<Fasilitas> fasilitasList = k.getFasilitasList();
                        for (int i = 0; i < fasilitasList.size(); i++) {
                            out.print(fasilitasList.get(i).getNamaFasilitas());
                            if (i < fasilitasList.size() - 1) {
                                out.print(", ");
                            }
                        }
                    %>
                </p>
                <p><strong>Max Guests:</strong> <%= k.getMaxGuest() %> Guests</p>
                <p class="mt-3">Nikmati kenyamanan menginap di kamar <%= k.getTipe() %> kami dengan fasilitas terbaik.</p>
                <p><a href="${pageContext.request.contextPath}/#" class="btn btn-primary text-white">Book Now</a></p>
            </div>
        </div>
        <% delay += 100; } %>
    </div>
</section>
<% } %>

<!-- CTA SECTION -->
<section class="section bg-image overlay" style="background-image: url('${pageContext.request.contextPath}/images/hero_4.jpg');">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-md-6 text-center mb-4 mb-md-0 text-md-left" data-aos="fade-up">
                <h2 class="text-white font-weight-bold">A Best Place To Stay. Reserve Now!</h2>
            </div>
            <div class="col-12 col-md-6 text-center text-md-right" data-aos="fade-up" data-aos-delay="200">
                <a href="${pageContext.request.contextPath}/Rooms" class="btn btn-outline-white-primary py-3 text-white px-5">Reserve</a>
            </div>
        </div>
    </div>
</section>
