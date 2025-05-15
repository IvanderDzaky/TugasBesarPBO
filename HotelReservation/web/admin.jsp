<!-- Hero Section -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Admin Dashboard</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp">Home</a></li>
                    <li>&bullet;</li>
                    <li>Admin Dashboard</li>
                </ul>
            </div>
        </div>
    </div>
</section>
<!-- END Hero Section -->

<!-- Reservations Management Section -->
<div class="container mt-5 mb-5">
    <h2 class="mb-4">Panel Admin</h2>

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

    <div class="tab-content" id="adminTabsContent">
        <!-- Customer Tab -->
        <div class="tab-pane fade show active" id="customer" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h4>Data Customer</h4>
                <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#tambahCustomerModal">Tambah Customer</button>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr><th>Nama</th><th>Email</th><th>Aksi</th></tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Amanda</td>
                        <td>amanda@mail.com</td>
                        <td>
                            <button class="btn btn-sm btn-warning">Edit</button>
                            <button class="btn btn-sm btn-danger">Hapus</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Kamar Tab -->
        <div class="tab-pane fade" id="kamar" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h4>Data Kamar</h4>
                <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#tambahKamarModal">Tambah Kamar</button>
            </div>
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
                            <button class="btn btn-sm btn-warning">Edit</button>
                            <button class="btn btn-sm btn-danger">Hapus</button>
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
                            <button class="btn btn-sm btn-danger">Hapus</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
