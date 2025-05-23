CREATE DATABASE IF NOT EXISTS hotelreservation;
USE hotelreservation;

-- Tabel User
CREATE TABLE users (
  id_user INT PRIMARY KEY AUTO_INCREMENT,
  nama VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  isAdmin TINYINT NOT NULL DEFAULT 0, -- 1 = Admin, 0 = Customer
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Kamar
CREATE TABLE kamar (
  id_kamar INT PRIMARY KEY AUTO_INCREMENT,
  nomor_kamar VARCHAR(10) UNIQUE NOT NULL,
  tipe VARCHAR(50) NOT NULL,
  harga double NOT NULL,
  max_guest INT NOT NULL DEFAULT 1,
  status TINYINT DEFAULT 1 -- 1: tersedia, 0: tidak tersedia
);

-- Tabel Fasilitas
CREATE TABLE fasilitas (
  id_fasilitas INT PRIMARY KEY AUTO_INCREMENT,
  nama_fasilitas VARCHAR(100) NOT NULL UNIQUE,
nama_fasilitas VARCHAR(255) NOT NULL
);

-- Tabel Many-to-Many: Kamar <-> Fasilitas
CREATE TABLE kamar_fasilitas (
  id_kamar INT,
  id_fasilitas INT,
  PRIMARY KEY (id_kamar, id_fasilitas),
  FOREIGN KEY (id_kamar) REFERENCES kamar(id_kamar) ON DELETE CASCADE,
  FOREIGN KEY (id_fasilitas) REFERENCES fasilitas(id_fasilitas) ON DELETE CASCADE
);

-- Tabel Reservasi
CREATE TABLE reservasi (
  id_reservasi INT PRIMARY KEY AUTO_INCREMENT,
  id_user INT NOT NULL,
  id_kamar INT NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  status VARCHAR(20) DEFAULT 'Dipesan',
  FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_kamar) REFERENCES kamar(id_kamar) ON DELETE CASCADE
);

CREATE TABLE pembayaran (
  id_pembayaran VARCHAR(50) PRIMARY KEY,
  id_reservasi INT NOT NULL,
  metode VARCHAR(50),
  jumlah_bayar DOUBLE,
  status VARCHAR(20), -- contoh: 'Sukses', 'Pending', 'Gagal'
  tanggal_bayar DATE DEFAULT CURRENT_DATE,
  FOREIGN KEY (id_reservasi) REFERENCES reservasi(id_reservasi) ON DELETE CASCADE
);
