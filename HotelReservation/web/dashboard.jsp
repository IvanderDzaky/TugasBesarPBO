<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="hotel.model.*" %>


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
                            Map<Integer, String> statusPembayaranMap = (Map<Integer, String>) request.getAttribute("statusPembayaranMap");
                            if (daftarReservasi != null && !daftarReservasi.isEmpty()) {
                                for (Reservasi r : daftarReservasi) {
                                    String status = r.getStatus();
                                    String badgeStatus = "secondary";
                                    if ("Dipesan".equalsIgnoreCase(status)) {
                                        badgeStatus = "warning";
                                    } else if ("Selesai".equalsIgnoreCase(status)) {
                                        badgeStatus = "secondary";
                                    } else if ("Dibatalkan".equalsIgnoreCase(status)) {
                                        badgeStatus = "danger";
                                    }

                                    int idReservasi = r.getIdReservasi();
                                    String statusPembayaran = statusPembayaranMap.getOrDefault(idReservasi, "Belum Bayar");
                                    String badgePembayaran = "danger";
                                    boolean disableAksi = false;

                                    if ("Lunas".equalsIgnoreCase(statusPembayaran)) {
                                        badgePembayaran = "success";
                                    } else if ("Dibatalkan".equalsIgnoreCase(statusPembayaran)) {
                                        badgePembayaran = "secondary";
                                    }

                                    // Jika reservasi selesai atau dibatalkan, aksi harus dinonaktifkan
                                    if ("Selesai".equalsIgnoreCase(status) || "Dibatalkan".equalsIgnoreCase(status)) {
                                        disableAksi = true;
                                    }

                        %>
                        <tr>
                            <td><%= r.getKamar().getNomorKamar()%></td>
                            <td><%= r.getKamar().getTipe()%></td>
                            <td><%= r.getCheckIn()%></td>
                            <td><%= r.getCheckOut()%></td>
                            <td><span class="badge badge-<%= badgeStatus%>"><%= status%></span></td>
                            <td><span class="badge badge-<%= badgePembayaran%>"><%= statusPembayaran %></span></td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-primary btn-ubah" data-id="<%= r.getIdReservasi()%>" <%= disableAksi ? "disabled" : ""%>>
                                    Ubah
                                </button>

                                <form method="get" action="Dashboard" style="display:inline;">
                                    <input type="hidden" name="action" value="batalkanReservasi">
                                    <input type="hidden" name="idReservasi" value="<%= r.getIdReservasi()%>">
                                    <button type="submit" class="btn btn-sm btn-outline-danger"
                                            onclick="return confirm('Yakin hapus reservasi ini?')"
                                            <%= disableAksi ? "disabled" : ""%>>
                                        Batalkan
                                    </button>
                                </form>
                                <form method="post" action="Payment" style="display:inline;">
                                    <input type="hidden" name="idReservasi" value="<%= r.getIdReservasi()%>">
                                    <button type="submit"
                                            class="btn btn-sm btn-outline-success"
                                            <%= disableAksi ? "disabled" : ""%>>
                                        Bayar
                                    </button>
                                </form>

                            </td>
                        </tr>
                        <!-- Form ubah tampilkan jika diperlukan -->
                        <tr class="ubah-form-row" style="display:none;">
                            <td colspan="7">
                                <form class="ubah-form" method="post" action="Dashboard?action=ubahReservasi">
                                    <div class="form-row">
                                        <input type="hidden" name="idReservasi" value="<%= r.getIdReservasi()%>">

                                        <div class="col-md-3">
                                            <input type="date" class="form-control" placeholder="Check-In Baru" name="newCheckIn">
                                        </div>
                                        <div class="col-md-3">
                                            <input type="date" class="form-control" placeholder="Check-Out Baru" name="newCheckOut">
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
                                                        $('.ubah-form-row').hide();
                                                        const currentRow = $(this).closest('tr');
                                                        const ubahRow = currentRow.next('.ubah-form-row');
                                                        ubahRow.show();
                                                    });

                                                    $('.btn-batal').click(function () {
                                                        $(this).closest('.ubah-form-row').hide();
                                                    });

                                                    $('.btn-simpan').click(function () {
                                                        const form = $(this).closest('form');
                                                        const checkIn = form.find('input[name="newCheckIn"]').val();
                                                        const checkOut = form.find('input[name="newCheckOut"]').val();

                                                        if (!checkIn || !checkOut) {
                                                            alert('Tanggal tidak boleh kosong!');
                                                            return;
                                                        }

                                                        form.submit();
                                                    });
                                                });

</script>
