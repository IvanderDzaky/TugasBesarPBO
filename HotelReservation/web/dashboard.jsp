<!-- Hero Section -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">User  Profile</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp">Home</a></li>
                    <li>&bullet;</li>
                    <li>User Profile</li>
                </ul>
            </div>
        </div>
    </div>
</section>
<!-- END Hero Section -->
<div class="container mt-5 mb-5" style="margin-top: 50px;" style= "background-image: url(images/foto_1.jpg)">
    <h3 class="mb-4">Riwayat Reservasi</h3>
    <table class="table table-hover shadow-sm">
        <thead class="thead-light">
            <tr>
                <th>ID</th>
                <th>Tipe Kamar</th>
                <th>Check-In</th>
                <th>Check-Out</th>
                <th>Status</th>
                <th>Pembayaran</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <!-- Contoh data dummy -->
            <tr>
                <td>R00123</td>
                <td>Deluxe</td>
                <td>2025-06-10</td>
                <td>2025-06-15</td>
                <td><span class="badge badge-warning">Dipesan</span></td>
                <td><span class="badge badge-danger">Belum Bayar</span></td>
                <td>
                    <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#ubahModal">Ubah</button>
                    <button class="btn btn-sm btn-danger">Batalkan</button>
                    <button class="btn btn-sm btn-success">Bayar</button>
                </td>
            </tr>
            <tr>
                <td>R00110</td>
                <td>Suite</td>
                <td>2025-04-01</td>
                <td>2025-04-05</td>
                <td><span class="badge badge-secondary">Selesai</span></td>
                <td><span class="badge badge-success">Lunas</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-secondary" disabled>Ubah</button>
                    <button class="btn btn-sm btn-outline-secondary" disabled>Batalkan</button>
                    <button class="btn btn-sm btn-outline-secondary" disabled>Bayar</button>
                </td>
            </tr>
        </tbody>
    </table>
</div>

<!-- Modal Ubah Reservasi -->
<div class="modal fade" id="ubahModal" tabindex="-1" role="dialog" aria-labelledby="ubahModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ubahModalLabel">Ubah Reservasi</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Tutup">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form>
          <div class="modal-body">
              <div class="form-group">
                  <label for="tanggalBaruCheckIn">Tanggal Check-In Baru</label>
                  <input type="date" class="form-control" id="tanggalBaruCheckIn">
              </div>
              <div class="form-group">
                  <label for="tanggalBaruCheckOut">Tanggal Check-Out Baru</label>
                  <input type="date" class="form-control" id="tanggalBaruCheckOut">
              </div>
              <div class="form-group">
                  <label for="tipeBaru">Tipe Kamar</label>
                  <select class="form-control" id="tipeBaru">
                      <option>Standar</option>
                      <option>Deluxe</option>
                      <option>Suite</option>
                  </select>
              </div>
          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Batal</button>
              <button type="submit" class="btn btn-primary">Simpan Perubahan</button>
          </div>
      </form>
    </div>
  </div>
</div>
