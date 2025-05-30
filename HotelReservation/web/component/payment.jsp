<%@ page import="hotel.model.*" %>
<%@ page import="hotel.helper.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.NumberFormat, java.text.SimpleDateFormat, java.util.Locale" %>

<%
    Reservasi reservasi = (Reservasi) request.getAttribute("reservasi");
    Kamar kamar = (Kamar) request.getAttribute("kamar");
    Pembayaran pembayaran = (Pembayaran) request.getAttribute("pembayaran");
    Integer durasi = (Integer) request.getAttribute("durasi");
    Double totalHarga = (Double) request.getAttribute("totalHarga");

    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "US"));
    String formattedTotal = totalHarga != null ? formatter.format(totalHarga) : "";

    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    String deadlineText = pembayaran != null && pembayaran.getDeadLine() != null
            ? sdf.format(pembayaran.getDeadLine())
            : "Belum ditentukan";
    Long deadlineMillis = (Long) request.getAttribute("deadlineMillis");
    if (deadlineMillis == null) {
        deadlineMillis = 0L;
    }

%>

<!-- Hero Section -->
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

<!-- Section Utama -->
<section class="section contact-section" id="next">
    <div class="container">
        <% if (pembayaran == null) {%>
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
        <% } else {%>
        <h3 class="text-center mb-4">Status Pembayaran</h3>
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
                <input type="text" class="form-control" value="<%= formattedTotal%>" readonly>
            </div>
            <div class="col-md-6 mb-3">
                <label>Metode Pembayaran</label>
                <input type="text" class="form-control" value="<%= pembayaran.getMetode()%>" readonly>
            </div>
            <div class="col-md-6 mb-3">
                <label>Status Pembayaran</label>
                <input type="text" class="form-control" value="<%= pembayaran.getStatus()%>" readonly>
            </div>
            <div class="col-md-6 mb-3">
                <label>Batas Waktu Pembayaran</label>
                <input type="text" class="form-control" value="<%= deadlineText%>" readonly>
                <style>
                    #countdown {
                        color: red !important;
                        font-size: 20px !important;
                        visibility: visible !important;
                        opacity: 1 !important;
                    }
                </style>
                <div id="countdown" class="mt-2 text-danger font-weight-bold" data-deadline="<%= deadlineMillis%>">
                    memuat waktu...</div>
            </div>
            <div class="col-md-6 mb-3">
                <label>ID Pembayaran</label>
                <input type="text" class="form-control" value="<%= pembayaran.getIdPembayaran()%>" readonly>
            </div>
            <div class="col-md-12 mt-4">
                <form method="post" action="${pageContext.request.contextPath}/Payment/Konfirmasi">
                    <input type="hidden" name="idPembayaran" value="<%= pembayaran.getIdPembayaran()%>">
                    <button type="submit" class="btn btn-primary">Saya Sudah Bayar</button>
                    <a href="${pageContext.request.contextPath}/Dashboard" class="btn btn-secondary">Kembali</a>
                </form>
            </div>
        </div>
        <% }%>
    </div>
</section>
<!-- Countdown Timer -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const countdownEl = document.getElementById("countdown");
        console.log("countdownEl:", countdownEl); // Cek elemen ada atau gak

        if (!countdownEl)
            return;

        const deadline = parseInt(countdownEl.getAttribute("data-deadline"));
        console.log("deadline:", deadline);

        if (isNaN(deadline)) {
            countdownEl.textContent = "Batas waktu tidak valid";
            return;
        }

        function updateCountdown() {
            const now = new Date().getTime();
            const distance = deadline - now;
            console.log("distance:", distance);

            if (distance <= 0) {
                countdownEl.textContent = "Waktu pembayaran telah habis";
                return;
            }

            const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((distance % (1000 * 60)) / 1000);

            countdownEl.textContent = hours + " jam " + minutes + " menit " + seconds + " detik";
            console.log("Hitung:", hours, "jam", minutes, "menit", seconds, "detik");
        }

        updateCountdown();
        setInterval(updateCountdown, 1000);
    });</script>


