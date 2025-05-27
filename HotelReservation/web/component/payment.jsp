<%@ page import="hotel.model.Reservasi" %>
<%@ page import="hotel.model.Kamar" %>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>

<%
    Reservasi reservasi = (Reservasi) request.getAttribute("reservasi");
    Kamar kamar = (Kamar) request.getAttribute("kamar");
    Integer durasi = (Integer) request.getAttribute("durasi");
    Double totalHarga = (Double) request.getAttribute("totalHarga");

    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    String formattedTotal = totalHarga != null ? formatter.format(totalHarga) : "";
%>

<!-- Hero -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center">
                <h1 class="heading mb-3">Payments</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="Home">Home</a></li>
                    <li>&bullet;</li>
                    <li>Payments</li>
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

<% Boolean showCountdown = (Boolean) request.getAttribute("showCountdown"); %>
<% Long deadlineMillis = (Long) request.getAttribute("deadlineMillis"); %>

<% if (reservasi != null && kamar != null) {%>
<section class="section contact-section" id="next">
    <div class="container">
        <h3 class="text-center mb-4">Konfirmasi Pembayaran Reservasi</h3>
        <form method="post" action="${pageContext.request.contextPath}/Payment/Bayar">
            <input type="hidden" name="idReservasi" value="<%= reservasi.getIdReservasi()%>">

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label>Nomor Kamar</label>
                    <input type="text" class="form-control" value="<%= kamar.getNomorKamar()%>" readonly>
                </div>
                <div class="col-md-6 mb-3">
                    <label>Tipe Kamar</label>
                    <input type="text" class="form-control" value="<%= kamar.getTipe()%>" readonly>
                </div>

                <div class="col-md-6 mb-3">
                    <label>Durasi Menginap (malam)</label>
                    <input type="text" class="form-control" value="<%= durasi%>" readonly>
                </div>
                <div class="col-md-6 mb-3">
                    <label>Total Harga</label>
                    <input type="hidden" name="jumlahBayar" value="<%= totalHarga%>">
                    <input type="text" class="form-control" value="<%= formattedTotal%>" readonly>
                </div>

                <div class="col-md-6 mb-3">
                    <label>Metode Pembayaran</label>
                    <select name="metode" class="form-control" required>
                        <option value="" disabled selected>Pilih Metode</option>
                        <option value="Transfer Bank">Transfer Bank</option>
                        <option value="QRIS">QRIS</option>
                        <option value="E-Wallet">E-Wallet</option>
                    </select>
                </div>

                <div class="col-md-12 mt-3">
                    <button type="submit" class="btn btn-success">Bayar Sekarang</button>
                    <a href="${pageContext.request.contextPath}/Dashboard" class="btn btn-secondary">Kembali</a>
                </div>
            </div>
        </form>
    </div>
</section>

<% } else if (showCountdown != null && showCountdown) { %>
<section class="section pb-4">
    <div class="container">
        <h4 class="text-center mb-3">Menunggu Pembayaran...</h4>
        <p class="text-center">Silakan selesaikan pembayaran Anda dalam waktu <strong>24 jam</strong>.</p>

        <div id="countdown" class="text-center text-danger font-weight-bold mb-4" style="font-size: 24px;"></div>

        <% if (request.getAttribute("idPembayaran") != null) {%>
        <form method="post" action="${pageContext.request.contextPath}/Payment/Bayar/Konfirmasi">
            <input type="hidden" name="idPembayaran" value="<%= request.getAttribute("idPembayaran")%>">
            <div class="text-center">
                <button type="submit" class="btn btn-success">Saya Sudah Bayar</button>
                <a href="${pageContext.request.contextPath}/Dashboard" class="btn btn-secondary">Kembali</a>
            </div>
        </form>
        <% } %>
    </div>
</section>

<% if (deadlineMillis != null) {%>
<script>
    let deadline = <%= deadlineMillis%>;
    let countdown = document.getElementById("countdown");
    let interval = setInterval(function () {
        let now = new Date().getTime();
        let distance = deadline - now;

        if (distance <= 0) {
            clearInterval(interval);
            countdown.innerHTML = "Waktu habis";
        } else {
            let hrs = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            let mins = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            let secs = Math.floor((distance % (1000 * 60)) / 1000);
            countdown.innerHTML = hrs + " jam " + mins + " menit " + secs + " detik";
        }
    }, 1000);
</script>
<% } %>

<% } else { %>
<!-- Jika tidak ada data reservasi dan tidak ada countdown -->
<section class="section pb-4">
    <div class="container text-center">
        <h4>Data pembayaran tidak ditemukan.</h4>
        <a href="${pageContext.request.contextPath}/Dashboard" class="btn btn-primary mt-3">Kembali ke Dashboard</a>
    </div>
</section>
<% }%>
