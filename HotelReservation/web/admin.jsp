<%@ page import="java.util.*" %>
<%@ page import="hotel.model.*" %>
<%@ page import="hotel.helper.*" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!-- Hero Section -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Admin Dashboard</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="Home">Home</a></li>
                    <li>&bullet;</li>
                    <li>Admin Dashboard</li>
                </ul>
            </div>
        </div>
    </div>
    <a class="mouse smoothscroll" href="#adminTabs">
        <div class="mouse-icon">
            <span class="mouse-wheel"></span>
        </div>
    </a>
</section>
<!-- END Hero Section -->

<!-- Admin Panel -->
<div class="container mt-5 mb-5">
    <h2 class="mb-4">Admin Panel</h2>

    <!-- Tabs Navigation -->
    <ul class="nav nav-tabs mb-4" id="adminTabs" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="customer-tab" data-toggle="tab" href="#customer" role="tab">Customer</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="kamar-tab" data-toggle="tab" href="#kamar" role="tab">Kamar</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="reservasi-tab" data-toggle="tab" href="#reservasi" role="tab">Reservasi</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="fasilitas-tab" data-toggle="tab" href="#fasilitas" role="tab">Fasilitas</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="profit-tab" data-toggle="tab" href="#profit" role="tab">Profit</a>
        </li>
    </ul>

    <!-- Tab Content -->
    <div class="tab-content" id="adminTabsContent">
        <!-- Customer Tab -->
        <div class="tab-pane fade show active" id="customer" role="tabpanel">
            <!-- Section Form & Tombol Tambah -->
            <div class="form-section-container">
                <div class="d-flex justify-content-between align-items-center mb-3 w-100 px-3">
                    <button class="btn btn-outline-primary btn-sm" onclick="toggleForm('customerForm')">Tambah Customer</button>
                </div>
                <!-- Tambah Customer Form -->
                <div id="customerForm" class="form-section mb-3 mt-3" style="display: none;">
                    <form class="row g-2 align-items-end" action="Admins?action=tambahCustomer" method="post">
                        <div class="col-md-3">
                            <input type="text" name="nama" class="form-control" placeholder="Nama" required>
                        </div>
                        <div class="col-md-3">
                            <input type="email" name="email" class="form-control" placeholder="Email" required>
                        </div>
                        <div class="col-md-3">
                            <input type="password" name="password" class="form-control" placeholder="Password" required>
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-success w-100">Simpan</button>
                        </div>
                    </form>
                </div>

                <!-- Edit Customer Form -->
                <div id="editCustomerForm" class="form-section mb-3 mt-3" style="display: none;">
                    <form class="row g-2 align-items-end" action="Admins?action=updateCustomer" method="post">
                        <input type="hidden" id="editIdUser" name="idUser">

                        <div class="col-md-3">
                            <input type="text" id="editNama" name="nama" class="form-control" placeholder="Nama" required>
                        </div>
                        <div class="col-md-3">
                            <input type="email" id="editEmail" name="email" class="form-control" placeholder="Email" required>
                        </div>
                        <div class="col-md-3">
                            <input type="password" id="editPassword" name="password" class="form-control" placeholder="Password" required>
                        </div>
                        <div class="col-md-1">
                            <button type="submit" class="btn btn-primary w-100">Update</button>
                        </div>
                        <div class="col-md-1">
                            <button type="button" class="btn btn-secondary w-100" onclick="hideEditForm()">Batal</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Section Tabel Data -->
            <div class="table-section-container">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Nama</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Dibuat Pada</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Customer> daftarCustomer = (List<Customer>) request.getAttribute("daftarCustomer");
                            if (daftarCustomer != null && !daftarCustomer.isEmpty()) {
                                for (Customer c : daftarCustomer) {
                        %>
                        <tr>
                            <td style="max-width: 200px; word-wrap: break-word; white-space: normal;"><%= c.getNama()%></td>
                            <td style="max-width: 200px; word-wrap: break-word; white-space: normal;"><%= c.getEmail()%></td>
                            <td style="max-width: 200px; word-wrap: break-word; white-space: normal;"><%= c.getPassword()%></td>
                            <td><%= c.getCreatedAt()%></td>
                            <td>
                                <button class="btn btn-sm btn-warning"
                                        onclick="showEditForm('<%= c.getIdUser()%>', '<%= c.getNama()%>', '<%= c.getEmail()%>', '<%= c.getPassword()%>')">
                                    Edit
                                </button>
                                <button class="btn btn-sm btn-danger"
                                        onclick="if (confirm('Yakin hapus data ini?')) {
                                                    window.location.href = 'Admins?action=hapusCustomer&idUser=<%= c.getIdUser()%>';
                                                }">
                                    Hapus
                                </button>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr>
                            <td colspan="5" class="text-center">Tidak ada data customer</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Kamar Tab -->
        <div class="tab-pane fade" id="kamar" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-3 w-100 px-3">
                <button class="btn btn-outline-primary btn-sm" onclick="toggleForm('kamarForm')">Tambah Kamar</button>
            </div>

            <!-- Kamar Form -->
            <% List<Fasilitas> daftarFasilitas = (List<Fasilitas>) request.getAttribute("daftarFasilitas"); %>
            <div id="kamarForm" class="form-section mb-3" style="display: none;">
                <form action="Admins?action=tambahKamar" method="post">
                    <div class="form-group">
                        <input type="text" name="nomor" class="form-control" placeholder="No Kamar" required>
                    </div>

                    <div class="form-group">
                        <select name="tipe" class="form-control" required>
                            <option value="Single Room">Single Room</option>
                            <option value="Family Room">Family Room</option>
                            <option value="Presidential Room">Presidential Room</option>
                            <option value="Suite">Suite</option>
                            <option value="VIP Suite">VIP Suite</option>
                            <option value="Deluxe Suite">Deluxe Suite</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <select name="harga" class="form-control" required>
                            <option value="90">90$</option>
                            <option value="120">120$</option>
                            <option value="250">250$</option>
                            <option value="300">300$</option>
                            <option value="350">350$</option>
                            <option value="400">400$</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <select name="maxGuest" class="form-control" required>
                            <option value="2">2</option>
                            <option value="6">5</option>
                            <option value="8">8</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="20">20</option>
                        </select>
                    </div>                
                    <div class="form-group">
                        <select name="status" class="form-control" required>
                            <option value="Tersedia">Tersedia</option>
                            <option value="Terisi">Terisi</option>
                        </select>
                    </div>
                    <div class="form-group mb-2 mr-sm-2">
                        <label>Fasilitas:</label>
                        <div class="d-flex flex-wrap">
                            <% if (daftarFasilitas != null) {
                                    for (Fasilitas f : daftarFasilitas) {%>
                            <div class="form-check mr-3">
                                <input class="form-check-input" type="checkbox" name="fasilitas" value="<%= f.getIdFasilitas()%>" id="fasilitas_<%= f.getIdFasilitas()%>">
                                <label class="form-check-label" for="fasilitas_<%= f.getIdFasilitas()%>">
                                    <%= f.getNamaFasilitas()%>
                                </label>
                            </div>
                            <%  }
                                } %>
                        </div>
                    </div>
                    <div class="form-group mt-3">
                        <button type="submit" class="btn btn-success">Simpan</button>
                    </div>
                </form>
            </div>


            <!-- Kamar Table -->
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nomor</th><th>Tipe</th><th>Harga</th><th>Max Guest</th><th>Fasilitas</th><th>Status</th><th>Aksi</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Kamar> daftarKamar = (List<Kamar>) request.getAttribute("daftarKamar");
                        if (daftarKamar != null && !daftarKamar.isEmpty()) {
                            for (Kamar k : daftarKamar) {
                    %>
                    <tr>
                        <td><%= k.getNomorKamar()%></td>
                        <td><%= k.getTipe()%></td>
                        <td>$<%= k.getHarga()%></td>
                        <td><%= k.getMaxGuest()%></td>
                        <td style="max-width: 200px; word-wrap: break-word; white-space: normal;">
                            <%
                                List<Fasilitas> fasilitasList = k.getFasilitasList();
                                for (int i = 0; i < fasilitasList.size(); i++) {
                                    out.print(fasilitasList.get(i).getNamaFasilitas());
                                    if (i < fasilitasList.size() - 1) {
                                        out.print(", ");
                                    }
                                }
                            %>
                        </td>
                        <td>
                            <span class="badge <%= k.isTersedia() ? "badge-success" : "badge-secondary"%>">
                                <%= k.isTersedia() ? "Tersedia" : "Terisi"%>
                            </span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-warning"
                                    data-id="<%= k.getIdKamar()%>"
                                    data-nomor="<%= k.getNomorKamar()%>"
                                    data-tipe="<%= k.getTipe()%>"
                                    data-harga="<%= k.getHarga()%>"
                                    data-maxguest="<%= k.getMaxGuest()%>"
                                    data-status="<%= k.isTersedia() ? "Tersedia" : "Terisi"%>"
                                    data-fasilitas="<%= String.join(",", k.getFasilitasList().stream().map(f -> String.valueOf(f.getIdFasilitas())).toArray(String[]::new))%>"
                                    onclick="showEditKamarForm(this)">
                                Edit
                            </button>
                            <button class="btn btn-sm btn-danger"
                                    onclick="if (confirm('Yakin hapus kamar ini?')) {
                                                window.location.href = 'Admins?action=hapusKamar&idKamar=<%= k.getIdKamar()%>';
                                            }">
                                Hapus
                            </button>
                        </td>
                    </tr>
                    <%  }
                    } else { %>
                    <tr>
                        <td colspan="7" class="text-center">Tidak ada data Kamar</td>
                    </tr>
                    <% } %>
                </tbody>
                <!-- Edit Kamar Form -->
                <div id="editKamarForm" class="form-section mb-3" style="display: none;">
                    <form action="Admins?action=updateKamar" method="post">
                        <input type="hidden" id="editIdKamar" name="idKamar">

                        <div class="form-group">
                            <input type="text" id="editNomorKamar" name="nomorKamar" class="form-control" placeholder="Nomor Kamar" required>
                        </div>

                        <div class="form-group">
                            <select name="tipeKamar" id="editTipeKamar" class="form-control" required>
                                <option value="Single Room">Single Room</option>
                                <option value="Family Room">Family Room</option>
                                <option value="Presidential Room">Presidential Room</option>
                                <option value="Suite">Suite</option>
                                <option value="VIP Suite">VIP Suite</option>
                                <option value="Deluxe Suite">Deluxe Suite</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <input type="number" id="editHargaKamar" name="hargaKamar" class="form-control" placeholder="Harga Kamar" required>
                        </div>

                        <div class="form-group">
                            <input type="number" id="editMaxGuest" name="maxGuest" class="form-control" placeholder="Max Guest" required>
                        </div>                  
                        <div class="form-group">
                            <select id="editStatus" name="status" class="form-control" required>
                                <option value="Tersedia">Tersedia</option>
                                <option value="Terisi">Terisi</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Fasilitas:</label>
                            <div class="d-flex flex-wrap">
                                <% if (daftarFasilitas != null) {
                                        for (Fasilitas f : daftarFasilitas) {%>
                                <div class="form-check mr-3 mb-2">
                                    <input class="form-check-input editFasilitas" type="checkbox" name="fasilitas" value="<%= f.getIdFasilitas()%>" id="editFasilitas_<%= f.getIdFasilitas()%>">
                                    <label class="form-check-label" for="editFasilitas_<%= f.getIdFasilitas()%>">
                                        <%= f.getNamaFasilitas()%>
                                    </label>
                                </div>
                                <% }
                                    } %>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Update</button>
                            <button type="button" class="btn btn-secondary ml-2" onclick="hideEditForm()">Batal</button>
                        </div>
                    </form>
                </div>
            </table>
        </div>


        <%
            List<Reservasi> reservasiList = (List<Reservasi>) request.getAttribute("reservasiList");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        %>

        <!-- Reservasi Tab -->
        <div class="tab-pane fade" id="reservasi" role="tabpanel">
            <!-- Edit reservasi form -->
            <div id="editReservasiForm" class="form-section mb-3" style="display: none;">
                <form action="Admins?action=ubahReservasi" method="post">
                    <input type="hidden" id="editIdReservasi" name="idReservasi">
                    <div class="form-group">
                        <select name="status" id="editStatus" class="form-control" required>
                            <p>Status</p>
                            <option value="Dipesan">Dipesan</option>
                            <option value="Pending">Pending</option>
                            <option value="Ditempati">Ditempati</option>
                            <option value="Selesai">Selesai</option>
                            <option value="Dibatalkan">Dibatalkan</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Update</button>
                        <button type="button" class="btn btn-secondary ml-2" onclick="hideEditForm()">Batal</button>
                    </div>
                </form>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Kamar</th>
                        <th>Check-In</th>
                        <th>Check-Out</th>
                        <th>Status</th>
                        <th>Aksi</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (reservasiList == null || reservasiList.isEmpty()) {
                    %>
                    <tr><td colspan="7">Tidak ada data reservasi.</td></tr>
                    <%
                    } else {
                        for (Reservasi r : reservasiList) {
                    %>
                    <tr>
                        <td><%= r.getIdReservasi()%></td>
                        <td><%= r.getCustomer().getEmail()%></td>
                        <td><%= r.getKamar().getNomorKamar()%></td>
                        <td><%= sdf.format(r.getCheckIn())%></td>
                        <td><%= sdf.format(r.getCheckOut())%></td>
                        <td>
                            <%
                                String status = r.getStatus();
                                String badge = "badge-secondary";
                                if ("Dipesan".equals(status))
                                    badge = "badge-warning";
                                else if ("CheckIn".equals(status))
                                    badge = "badge-primary";
                                else if ("Selesai".equals(status))
                                    badge = "badge-success";
                            %>
                            <span class="badge <%= badge%>"><%= status%></span>
                        </td>
                        <td>
                            <button class="btn btn-outline-primary"
                                    data-id="<%= r.getIdReservasi()%>"
                                    data-status="<%= r.getStatus()%>"
                                    onClick="showUpdateReservasiForm(this)">                                   
                                Edit
                            </button>
                            <button class="btn btn-sm btn-danger"
                                    onclick="if (confirm('Yakin hapus reservasi ini?')) {
                                                window.location.href = 'Admins?action=hapusReservasi&idReservasi=<%= r.getIdReservasi()%>';
                                            }">
                                Hapus
                            </button>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!-- Fasilitas Tab -->
        <div class="tab-pane fade" id="fasilitas" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-3 w-100 px-3">
                <button class="btn btn-outline-primary btn-sm" onclick="toggleForm('fasilitasForm')">Tambah Fasilitas</button>
            </div>
            <!-- Fasilitas Form -->
            <div id="fasilitasForm" class="form-section mb-3" style="display: none;">
                <form action="Admins?action=tambahFasilitas" method="post">
                    <div class="form-group">
                        <input type="text" name="nama" class="form-control mb-2" placeholder="Nama Fasilitas" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="deskripsi" class="form-control mb-2" placeholder="Deskripsi Fasilitas" required>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Simpan</button>
                    </div>
                </form>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nama Fasilitas</th>
                        <th>Deskripsi Fasilitas</th>
                        <th>Aksi</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (daftarFasilitas != null) {
                            for (Fasilitas f : daftarFasilitas) {
                    %>
                    <tr>
                        <td><%= f.getNamaFasilitas()%></td>
                        <td><%= f.getDeskripsiFasilitas()%></td>
                        <td>
                            <button class="btn btn-sm btn-danger"
                                    onclick="if (confirm('Yakin ingin menghapus fasilitas ini?')) {
                                                window.location.href = 'Admins?action=hapusFasilitas&idFasilitas=<%= f.getIdFasilitas()%>';
                                            }">
                                Hapus
                            </button>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="3">Tidak ada fasilitas ditemukan.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!-- profit tab -->
        <div class="tab-pane fade" id="profit" role="tabpanel">
            <h5>Profit</h5>
            <div class="d-flex justify-content-between align-items-center mb-3 w-100 px-3">
                <%@ page import="java.util.List" %>
                <%@ page import="hotel.helper.Profit" %>
                <%
                    List<Profit> profitList = (List<Profit>) request.getAttribute("profit");
                    if (profitList != null && !profitList.isEmpty()) {
                %>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Tahun</th>
                            <th>Bulan</th>
                            <th>Total Profit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Profit p : profitList) {
                        %>
                        <tr>
                            <td><%= p.getTahun()%></td>
                            <td><%= p.getBulan()%></td>
                            <td>$<%= String.format("%.2f", p.getTotal())%></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <%
                } else {
                %>
                <p>Tidak ada data profit.</p>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script>
                                        function showEditForm(id, nama, email, password) {
                                            document.getElementById('editIdUser').value = id;
                                            document.getElementById('editNama').value = nama;
                                            document.getElementById('editEmail').value = email;
                                            document.getElementById('editPassword').value = password;

                                            // Tampilkan form edit
                                            document.getElementById('editCustomerForm').style.display = 'block';

                                            // Sembunyikan form tambah (opsional)
                                            document.getElementById('customerForm').style.display = 'none';
                                        }

                                        function hideEditForm() {
                                            document.getElementById('editCustomerForm').style.display = 'none';
                                            document.getElementById('editKamarForm').style.display = 'none';
                                            document.getElementById('editReservasiForm').style.display = 'none';

                                        }

                                        function hideTambahForm() {
                                            document.getElementById('customerForm').style.display = 'none';
                                            document.getElementById('kamarForm').style.display = 'none';
                                        }

                                        function toggleForm(formId) {
                                            const form = document.getElementById(formId);
                                            const isHidden = window.getComputedStyle(form).display === 'none';
                                            form.style.display = isHidden ? 'block' : 'none';

                                            // Sembunyikan form edit saat membuka form tambah
                                            if (formId === 'kamarForm') {
                                                document.getElementById('editKamarForm').style.display = 'none';
                                            } else if (formId === 'customerForm') {
                                                document.getElementById('editCustomerForm').style.display = 'none';
                                            } else if (formId === 'reservasiForm') {
                                                document.getElementById('editReservasiForm').style.display = 'none';
                                            }
                                        }
                                        function showEditKamarForm(button) {
                                            document.getElementById('editKamarForm').style.display = 'block';

                                            document.getElementById('editIdKamar').value = button.getAttribute('data-id');
                                            document.getElementById('editNomorKamar').value = button.getAttribute('data-nomor');
                                            document.getElementById('editTipeKamar').value = button.getAttribute('data-tipe');
                                            document.getElementById('editHargaKamar').value = button.getAttribute('data-harga');
                                            document.getElementById('editMaxGuest').value = button.getAttribute('data-maxguest');
                                            document.getElementById('editStatus').value = button.getAttribute('data-status');

                                            // Uncheck semua fasilitas dulu
                                            document.querySelectorAll('.editFasilitas').forEach(cb => cb.checked = false);

                                            // Tambahkan logika untuk men-check fasilitas yang sesuai jika ingin realtime (misal lewat data-fasilitas dari tombol)
                                            const fasilitasStr = button.getAttribute('data-fasilitas');
                                            if (fasilitasStr) {
                                                const ids = fasilitasStr.split(',');
                                                document.querySelectorAll('.editFasilitas').forEach(cb => {
                                                    if (ids.includes(cb.value))
                                                        cb.checked = true;
                                                });
                                            }
                                            // Sembunyikan form tambah (opsional)
                                            document.getElementById('kamarForm').style.display = 'none';
                                        }

                                        function showUpdateReservasiForm(button) {
                                            const id = button.getAttribute("data-id");
                                            const status = button.getAttribute("data-status");

                                            document.getElementById("editIdReservasi").value = id;
                                            document.getElementById("editStatus").value = status;

                                            document.getElementById("editReservasiForm").style.display = "block";
                                        }
</script>
