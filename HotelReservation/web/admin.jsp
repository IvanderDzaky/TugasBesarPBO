<%@ page import="java.util.*" %>
<%@ page import="hotel.model.*" %>
<!-- Hero Section -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Admin Dashboard</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp?page=home">Home</a></li>
                    <li>&bullet;</li>
                    <li>Admin Dashboard</li>
                </ul>
            </div>
        </div>
    </div>
</section>
<!-- END Hero Section -->

<!-- Admin Panel -->
<div class="container mt-5 mb-5">
    <h2 class="mb-4">Panel Admin</h2>

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
    </ul>

    <!-- Tab Content -->
    <div class="tab-content" id="adminTabsContent">

        <!-- Customer Tab -->
        <div class="tab-pane fade show active" id="customer" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h4>Data Customer</h4>
                <button class="btn btn-outline-primary btn-sm" onclick="toggleForm('customerForm')">Tambah</button>
            </div>

            <!-- Customer Form -->
            <div id="customerForm" class="form-section mb-3" style="display: none;">
                <form class="form-inline" action="AdminController?action=tambahCustomer" method="post">
                    <input type="text" name="nama" class="form-control mb-2 mr-sm-2" placeholder="Nama" required>
                    <input type="email" name="email" class="form-control mb-2 mr-sm-2" placeholder="Email" required>
                    <input type="password" name="password" class="form-control mb-2 mr-sm-2" placeholder="Password" required>
                    <button type="submit" class="btn btn-success mb-2">Simpan</button>
                    <button type="button" class="btn btn-secondary mb-2 ml-2" onclick="hideTambahForm()">Batal</button>
                </form>
            </div>

            <!-- Customer Table -->
            <table class="table table-hover">
                <thead>
                    <tr><th>Nama</th><th>Email</th><th>Password</th><th>Dibuat Pada</th><th>Aksi</th></tr>
                </thead>
                <tbody>
                    <%
                        List<hotel.model.Customer> daftarCustomer = (List<hotel.model.Customer>) request.getAttribute("daftarCustomer");
                        if (daftarCustomer != null && !daftarCustomer.isEmpty()) {
                            for (hotel.model.Customer c : daftarCustomer) {
                    %>
                    <tr>
                        <td><%= c.getNama()%></td>
                        <td><%= c.getEmail()%></td>
                        <td><%= c.getPassword()%></td>
                        <td><%= c.getCreatedAt()%></td>
                        <td>
                            <button class="btn btn-sm btn-warning"
                                    onclick="showEditForm('<%= c.getIdUser()%>', '<%= c.getNama()%>', '<%= c.getEmail()%>', '<%= c.getPassword()%>')">
                                Edit
                            </button>

                            <a href="AdminController?action=hapusCustomer&idUser=<%= c.getIdUser()%>" class="btn btn-sm btn-danger" onclick="return confirm('Yakin hapus data ini?')">Hapus</a>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="3" class="text-center">Tidak ada data customer</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
                <!-- Edit Customer Form -->
                <div id="editCustomerForm" class="form-section mb-3" style="display: none;">
                    <form class="form-inline" action="AdminController?action=updateCustomer" method="post">
                        <input type="hidden" id="editIdUser" name="idUser">
                        <input type="text" id="editNama" name="nama" class="form-control mb-2 mr-sm-2" placeholder="Nama" required>
                        <input type="email" id="editEmail" name="email" class="form-control mb-2 mr-sm-2" placeholder="Email" required>
                        <input type="password" id="editPassword" name="password" class="form-control mb-2 mr-sm-2" placeholder="Password" required>
                        <button type="submit" class="btn btn-primary mb-2">Update</button>
                        <button type="button" class="btn btn-secondary mb-2 ml-2" onclick="hideEditForm()">Batal</button>
                    </form>
                </div>

            </table>
        </div>

        <!-- Kamar Tab -->
        <div class="tab-pane fade" id="kamar" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h4>Data Kamar</h4>
                <button class="btn btn-outline-primary btn-sm" onclick="toggleForm('kamarForm')">Tambah</button>
            </div>

            <!-- Kamar Form -->
            <div id="kamarForm" class="form-section mb-3" style="display: none;">
                <form class="form-inline" action="RoomController" method="post">
                    <input type="text" name="nomor" class="form-control mb-2 mr-sm-2" placeholder="No Kamar" required>
                    <input type="text" name="tipe" class="form-control mb-2 mr-sm-2" placeholder="Tipe" required>
                    <input type="number" name="harga" class="form-control mb-2 mr-sm-2" placeholder="Harga" required>
                    <select name="status" class="form-control mb-2 mr-sm-2" required>
                        <option value="Tersedia">Tersedia</option>
                        <option value="Terisi">Terisi</option>
                    </select>
                    <button type="submit" class="btn btn-success mb-2">Simpan</button>
                </form>
            </div>

            <!-- Kamar Table -->
            <table class="table table-hover">
                <thead>
                    <tr><th>Nomor</th><th>Tipe</th><th>Harga</th><th>Status</th><th>Aksi</th></tr>
                </thead>
                <tbody>
                    <tr>
                        <td>101</td>
                        <td>Deluxe</td>
                        <td>Rp500.000</td>
                        <td><span class="badge badge-success">Tersedia</span></td>
                        <td>
                            <a href="RoomController?action=edit&id=101" class="btn btn-sm btn-warning">Edit</a>
                            <a href="RoomController?action=delete&id=101" class="btn btn-sm btn-danger" onclick="return confirm('Yakin hapus kamar ini?')">Hapus</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Reservasi Tab -->
        <div class="tab-pane fade" id="reservasi" role="tabpanel">
            <h4>Data Reservasi</h4>
            <table class="table table-hover">
                <thead>
                    <tr><th>ID</th><th>Customer</th><th>Kamar</th><th>Status</th><th>Aksi</th></tr>
                </thead>
                <tbody>
                    <tr>
                        <td>R00123</td>
                        <td>Amanda</td>
                        <td>101</td>
                        <td><span class="badge badge-warning">Dipesan</span></td>
                        <td>
                            <a href="ReservationController?action=delete&id=R00123" class="btn btn-sm btn-danger" onclick="return confirm('Yakin hapus reservasi ini?')">Hapus</a>
                        </td>
                    </tr>
                </tbody>
            </table>
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
                                }
                                function hideTambahForm() {
                                    document.getElementById('customerForm').style.display = 'none';
                                }
                                function toggleForm(formId) {
                                    const form = document.getElementById(formId);
                                    form.style.display = form.style.display === 'none' ? 'block' : 'none';

                                    // Sembunyikan form edit kalau form tambah dibuka
                                    if (formId === 'customerForm') {
                                        document.getElementById('editCustomerForm').style.display = 'none';
                                    }
                                }
</script>
