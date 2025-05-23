<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="hotel.model.Reservasi" %>
<%@ page import="hotel.model.Kamar" %>

<!-- Hero -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center">
                <h1 class="heading mb-3">User Profile</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="Home">Home</a></li>
                    <li>&bullet;</li>
                    <li>User Profile</li>
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

<!-- Main Panel -->
<div class="container mt-5 mb-5" id="next">
    <!-- Tabs -->
    <ul class="nav nav-pills justify-content-center mb-4" id="userTab" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="profile-tab" data-toggle="pill" href="#profilePanel" role="tab">Profile Detail</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="reservation-tab" data-toggle="pill" href="#reservationPanel" role="tab">Reservation List</a>
        </li>
    </ul>

    <div class="tab-content bg-white shadow p-4 rounded">
        <!-- Profile -->
        <div class="tab-pane fade show active" id="profilePanel" role="tabpanel">
            <div class="card border-0">
                <div class="card-body">
                    <h5 class="mb-4 font-weight-bold">Basic Information</h5>
                    <form>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label>Name</label>
                                <input type="text" class="form-control" value="${userName}" readonly>
                            </div>
                            <div class="form-group col-md-4">
                                <label>Email</label>
                                <input type="text" class="form-control" value="${userEmail}" readonly>
                            </div>
                            <div class="form-group col-md-4">
                                <label>Account Created</label>
                                <input type="text" class="form-control" value="${userCreatedAt}" readonly>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Reservation List -->
        <div class="tab-pane fade" id="reservationPanel" role="tabpanel">
            <h4 class="mb-3">Riwayat Reservasi</h4>
            <div class="table-responsive">
                <table class="table table-bordered table-hover" id="reservationTable">
                    <thead class="thead-light text-center">
                        <tr>
                            <th>Nomor Kamar</th>
                            <th>Tipe Kamar</th>
                            <th>Check-In</th>
                            <th>Check-Out</th>
                            <th>Status</th>
                            <th>Pembayaran</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody class="text-center">
                        <%
                            List<Reservasi> daftarReservasi = (List<Reservasi>) request.getAttribute("daftarReservasi");

                            if (daftarReservasi != null && !daftarReservasi.isEmpty()) {
                                for (Reservasi r : daftarReservasi) {
                                    String status = r.getStatus();
                                    String badgeStatus = "secondary";
                                    if ("Dipesan".equalsIgnoreCase(status)) {
                                        badgeStatus = "warning";
                                    } else if ("Selesai".equalsIgnoreCase(status)) {
                                        badgeStatus = "secondary";
                                    }

                                    // Dummy untuk pembayaran (belum diimplementasi)
                                    String pembayaran = "-";
                                    String badgePembayaran = "danger";
                                    boolean disableAksi = false;

                                    if ("Selesai".equalsIgnoreCase(status)) {
                                        pembayaran = "Lunas";
                                        badgePembayaran = "success";
                                        disableAksi = true;
                                    } else {
                                        pembayaran = "Belum Bayar";
                                        badgePembayaran = "danger";
                                    }

                                    
                        %>
                        <tr>
                            <td><%= r.getKamar().getNomorKamar()%></td>
                            <td><%= r.getKamar().getTipe()%></td>
                            <td><%= r.getCheckIn()%></td>
                            <td><%= r.getCheckOut()%></td>
                            <td><span class="badge badge-<%= badgeStatus%>"><%= status%></span></td>
                            <td><span class="badge badge-<%= badgePembayaran%>"><%= pembayaran%></span></td>
                            <td>
                                <button class="btn btn-sm btn-outline-primary btn-ubah" <%= disableAksi ? "disabled" : ""%>>Ubah</button>
                                <button class="btn btn-sm btn-outline-danger" <%= disableAksi ? "disabled" : ""%>>Batalkan</button>
                                <button class="btn btn-sm btn-outline-success" <%= disableAksi ? "disabled" : ""%>>Bayar</button>
                            </td>
                        </tr>
                        <!-- Form ubah tampilkan jika diperlukan -->
                        <tr class="ubah-form-row" style="display:none;">
                            <td colspan="7">
                                <form class="ubah-form">
                                    <div class="form-row">
                                        <div class="col-md-3">
                                            <input type="date" class="form-control" placeholder="Check-In Baru" name="newCheckIn">
                                        </div>
                                        <div class="col-md-3">
                                            <input type="date" class="form-control" placeholder="Check-Out Baru" name="newCheckOut">
                                        </div>
                                        <div class="col-md-3">
                                            <select class="form-control" name="newTipe">
                                                <option value="Standar">Standar</option>
                                                <option value="Deluxe">Deluxe</option>
                                                <option value="Suite">Suite</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3 text-right">
                                            <button type="button" class="btn btn-sm btn-outline-secondary btn-batal">Batal</button>
                                            <button type="submit" class="btn btn-sm btn-primary btn-simpan">Simpan Perubahan</button>
                                        </div>
                                    </div>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="7">Belum ada reservasi.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>

                </table>
            </div>
        </div>

    </div>
</div>

<!-- Script Section -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $('.btn-ubah').click(function () {
            // Sembunyikan semua form ubah terlebih dahulu
            $('.ubah-form-row').hide();

            // Tampilkan form di bawah baris yang diklik
            const currentRow = $(this).closest('tr');
            const ubahRow = currentRow.next('.ubah-form-row');
            ubahRow.show();
        });

        $('.btn-batal').click(function () {
            $(this).closest('.ubah-form-row').hide();
        });

        $('.btn-simpan').click(function () {
            alert('Perubahan berhasil disimpan (simulasi).');
            $(this).closest('.ubah-form-row').hide();
        });
    });
</script>
