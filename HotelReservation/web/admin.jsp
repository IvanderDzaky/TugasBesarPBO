<%@ page import="java.util.*" %>
<%@ page import="hotel.model.*" %>
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
        <li class="nav-item">
            <a class="nav-link" id="fasilitas-tab" data-toggle="tab" href="#fasilitas" role="tab">Fasilitas</a>
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
                <form class="form-inline" action="Admins?action=tambahCustomer" method="post">
                    <input type="text" name="nama" class="form-control mb-2 mr-sm-2" placeholder="Nama" required>
                    <input type="email" name="email" class="form-control mb-2 mr-sm-2" placeholder="Email" required>
                    <input type="password" name="password" class="form-control mb-2 mr-sm-2" placeholder="Password" required>
                    <button type="submit" class="btn btn-success mb-2">Simpan</button>
                </form>
            </div>

            <!-- Customer Table -->
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
                            <a href="Admins?action=hapusCustomer&idUser=<%= c.getIdUser()%>" 
                               class="btn btn-sm btn-danger" 
                               onclick="return confirm('Yakin hapus data ini?')">
                                Hapus
                            </a>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">Tidak ada data customer</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
                <!-- Edit Customer Form -->
                <div id="editCustomerForm" class="form-section mb-3" style="display: none;">
                    <form class="form-inline" action="Admins?action=updateCustomer" method="post">
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
            <% List<Fasilitas> daftarFasilitas = (List<Fasilitas>) request.getAttribute("daftarFasilitas"); %>
            <div id="kamarForm" class="form-section mb-3" style="display: none;">
                <form class="form-inline" action="Admins?action=tambahKamar" method="post">
                    <input type="text" name="nomor" class="form-control mb-2 mr-sm-2" placeholder="No Kamar" required>
                    <input type="text" name="tipe" class="form-control mb-2 mr-sm-2" placeholder="Tipe" required>
                    <input type="number" name="harga" class="form-control mb-2 mr-sm-2" placeholder="Harga" required>
                    <input type="number" name="maxGuest" class="form-control mb-2 mr-sm-2" placeholder="Max Guest" required>
                    <div class="form-control mb-2 mr-sm-2">
                        <% if (daftarFasilitas != null) {
                                for (Fasilitas f : daftarFasilitas) {%>
                        <label>
                            <input type="checkbox" name="fasilitas" value="<%= f.getIdFasilitas()%>">
                            <%= f.getNamaFasilitas()%>
                        </label>
                        <%  }
                            } %>
                    </div>
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
                            <a href="Admins?action=hapusKamar&idKamar=<%= k.getIdKamar()%>" class="btn btn-sm btn-danger"
                               onclick="return confirm('Yakin hapus kamar ini?')">Hapus</a>
                        </td>
                    </tr>
                    <%  }
                    } else { %>
                    <tr>
                        <td colspan="7" class="text-center">Tidak ada data Kamar</td>
                    </tr>
                    <% } %>
                </tbody>
                <!-- Edit Kamar Form (Form tambah kamar hilang dan ini keluar tabel) -->
                <div id="editKamarForm" class="form-section mb-3" style="display: none;">
                    <form class="form-inline" action="Admins?action=updateKamar" method="post">
                        <input type="hidden" id="editIdKamar" name="idKamar">
                        <input type="text" id="editNomorKamar" name="nomorKamar" class="form-control mb-2 mr-sm-2" placeholder="Nomor Kamar" required>
                        <input type="text" id="editTipeKamar" name="tipeKamar" class="form-control mb-2 mr-sm-2" placeholder="Tipe Kamar" required>
                        <input type="number" id="editHargaKamar" name="hargaKamar" class="form-control mb-2 mr-sm-2" placeholder="Harga Kamar" required>
                        <input type="number" id="editMaxGuest" name="maxGuest" class="form-control mb-2 mr-sm-2" placeholder="Max Guest" required>
                        <div class="form-control mb-2 mr-sm-2">
                            <% if (daftarFasilitas != null) {
                                    for (Fasilitas f : daftarFasilitas) {%>
                            <label>
                                <input type="checkbox" class="editFasilitas" name="fasilitas" value="<%= f.getIdFasilitas()%>">
                                <%= f.getNamaFasilitas()%>
                            </label>
                            <%  }
                                } %>
                        </div>
                        <select id="editStatus" name="status" class="form-control mb-2 mr-sm-2" required>
                            <option value="Tersedia">Tersedia</option>
                            <option value="Terisi">Terisi</option>
                        </select>
                        <button type="submit" class="btn btn-primary mb-2">Update</button>
                        <button type="button" class="btn btn-secondary mb-2 ml-2" onclick="hideEditForm()">Batal</button>
                    </form>
                </div>
            </table>
        </div>



        <!-- Reservasi Tab -->
        <div class="tab-pane fade" id="reservasi" role="tabpanel">
            <h4>Data Reservasi</h4>
            <table class="table table-hover">
                <!-- Reservasi Form -->
                <div id="kamarForm" class="form-section mb-3" style="display: none;">
                    <form class="form-inline" action="Admins?action=tambahKamar" method="post">
                        <input type="text" name="nomor" class="form-control mb-2 mr-sm-2" placeholder="No Kamar" required>
                        <input type="text" name="tipe" class="form-control mb-2 mr-sm-2" placeholder="Tipe" required>
                        <input type="number" name="harga" class="form-control mb-2 mr-sm-2" placeholder="Harga" required>
                        <input type="number" name="Max Guest" class="form-control mb-2 mr-sm-2" placeholder="Max Guest" required>
                        <select many name="Fasilitas" class="form-control mb-2 mr-sm-2" required>
                            <option value="AC">AC</option>
                            <option value="Cleaning">Cleaning Service</option>
                            <option value="Shower">Shower</option>
                            <option value="TV">TV</option>
                            <option value="WiFI">WiFi</option>
                        </select>
                        <select name="status" class="form-control mb-2 mr-sm-2" required>
                            <option value="Tersedia">Tersedia</option>
                            <option value="Terisi">Terisi</option>
                        </select>
                        <button type="submit" class="btn btn-success mb-2">Simpan</button>
                    </form>
                </div>
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
                            <a href="Admins?action=delete&id=R00123" class="btn btn-sm btn-danger" onclick="return confirm('Yakin hapus reservasi ini?')">Hapus</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Fasilitas Tab -->
        <div class="tab-pane fade" id="fasilitas" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h4>Data Fasilitas</h4>
                <button class="btn btn-outline-primary btn-sm" onclick="toggleForm('fasilitasForm')">Tambah</button>
            </div>
            <!-- Fasilitas Form -->
            <div id="fasilitasForm" class="form-section mb-3" style="display: none;">
                <form class="form-inline" action="Admins?action=tambahFasilitas" method="post">
                    <input type="text" name="nama" class="form-control mb-2 mr-sm-2" placeholder="Nama Fasilitas" required>
                    <button type="submit" class="btn btn-success mb-2">Simpan</button>
                </form>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID Fasilitas</th>
                        <th>Nama Fasilitas</th>
                        <th>Aksi</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (daftarFasilitas != null) {
                            for (Fasilitas f : daftarFasilitas) {
                    %>
                    <tr>
                        <td><%= f.getIdFasilitas()%></td>
                        <td><%= f.getNamaFasilitas()%></td>
                        <td>
                            <a href="Admins?action=hapusFasilitas&idFasilitas=<%= f.getIdFasilitas()%>" class="btn btn-sm btn-danger"
                               onclick="return confirm('Yakin ingin menghapus fasilitas ini?')">Hapus</a>
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

</script>
