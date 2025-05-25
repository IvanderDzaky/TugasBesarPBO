-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2025 at 05:29 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Gunakan database hotelreservation
USE `hotelreservation`;

-- --------------------------------------------------------
-- Struktur tabel: users
-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `users` (
  `id_user` INT(11) NOT NULL AUTO_INCREMENT,
  `nama` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL UNIQUE,
  `password` VARCHAR(255) DEFAULT NULL,
  `isAdmin` TINYINT(4) NOT NULL DEFAULT 0,
  `createdAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data users
INSERT INTO `users` (`id_user`, `nama`, `email`, `password`, `isAdmin`, `createdAt`) VALUES
(23, 'mahmud', 'mahmud@gmail.com', '$2a$10$OzakO5ImwJ70GzUGh09TrufpiEs/mN0QmSzfxZj4E0s/kMjSA2YHS', 0, '2025-05-19 02:30:43'),
(38, 'fattan', 'fattan@gmail.com', '$2a$10$Fn1HcptBuu3RlwMc08IO4eagqGylCrz72WAwg/PfgG62z1MR8hgky', 0, '2025-05-24 07:09:32'),
(39, 'NIKO', 'Kayvon@gmail.com', '$2a$10$i5fc7WiJeMG4npJoMXeQWO5Sr/lWWhwNVf6aAMOIuMQ5R2h9ztwtq', 0, '2025-05-24 10:30:14'),
(40, 'ADMIN', 'admin@yahoo.com', '$2a$10$.gefvWczpIRlk2bCW1BdYelovWNT1KsSbEqY5PdvUvDZZC7wQMVU6', 1, '2025-05-24 10:37:53');

-- --------------------------------------------------------
-- Struktur tabel: kamar
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `kamar` (
  `id_kamar` INT(11) NOT NULL AUTO_INCREMENT,
  `nomor_kamar` VARCHAR(10) NOT NULL UNIQUE,
  `tipe` VARCHAR(50) NOT NULL,
  `harga` DOUBLE NOT NULL,
  `status` TINYINT(4) DEFAULT 1,
  `max_guest` INT(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_kamar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data kamar
INSERT INTO `kamar` (`id_kamar`, `nomor_kamar`, `tipe`, `harga`, `status`, `max_guest`) VALUES
(19, '101', 'Single Room', 90, 1, 2),
(20, '102', 'Single Room', 90, 1, 2),
(21, '103', 'Single Room', 90, 1, 2),
(22, '201', 'Family Room', 120, 1, 5),
(23, '202', 'Family Room', 120, 1, 5),
(24, '301', 'Presidential Room', 250, 1, 8),
(25, '401', 'Suite', 300, 1, 10),
(26, '402', 'Suite', 300, 1, 10),
(27, '501', 'VIP Suite', 350, 1, 15),
(28, '502', 'VIP Suite', 350, 1, 15),
(29, '601', 'Deluxe Suite', 400, 1, 20);

-- --------------------------------------------------------
-- Struktur tabel: fasilitas
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `fasilitas` (
  `id_fasilitas` INT(11) NOT NULL AUTO_INCREMENT,
  `nama_fasilitas` VARCHAR(100) NOT NULL UNIQUE,
  `deskripsi_fasilitas` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_fasilitas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data fasilitas
INSERT INTO `fasilitas` (`id_fasilitas`, `nama_fasilitas`, `deskripsi_fasilitas`) VALUES
(1, 'WiFi', 'High speed WiFi quality with Indihome 500mb/s.'),
(2, 'AC', 'Most coldest Air Conditioner that ever had.'),
(3, 'TV', 'Smart Tv'),
(4, 'Shower', 'Water Heater, Bathub'),
(5, 'Cleaning', 'Cleaning Service'),
(22, 'Coffe', 'Premium Coffe'),
(25, 'Balcony', 'Premium Balcony');

-- --------------------------------------------------------
-- Struktur tabel: kamar_fasilitas
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `kamar_fasilitas` (
  `id_kamar` INT(11) NOT NULL,
  `id_fasilitas` INT(11) NOT NULL,
  PRIMARY KEY (`id_kamar`, `id_fasilitas`),
  KEY `id_fasilitas` (`id_fasilitas`),
  CONSTRAINT `kamar_fasilitas_ibfk_1` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE,
  CONSTRAINT `kamar_fasilitas_ibfk_2` FOREIGN KEY (`id_fasilitas`) REFERENCES `fasilitas` (`id_fasilitas`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data kamar_fasilitas
INSERT INTO `kamar_fasilitas` (`id_kamar`, `id_fasilitas`) VALUES
(19, 1),(19, 2),(19, 3),(19, 4),
(20, 1),(20, 2),(20, 3),(20, 4),
(21, 1),(21, 2),(21, 3),(21, 4),
(22, 1),(22, 2),(22, 3),(22, 4),(22, 22),
(23, 1),(23, 2),(23, 3),(23, 4),(23, 22),
(24, 1),(24, 2),(24, 3),(24, 4),(24, 5),(24, 22),(24, 25),
(25, 1),(25, 2),(25, 3),(25, 4),(25, 5),(25, 22),(25, 25),
(26, 1),(26, 2),(26, 3),(26, 4),(26, 5),(26, 22),(26, 25),
(27, 1),(27, 2),(27, 3),(27, 4),(27, 5),(27, 22),(27, 25),
(28, 1),(28, 2),(28, 3),(28, 4),(28, 5),(28, 22),(28, 25),
(29, 1),(29, 2),(29, 3),(29, 4),(29, 5),(29, 22),(29, 25);

-- --------------------------------------------------------
-- Struktur tabel: pembayaran
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `pembayaran` (
  `id_pembayaran` VARCHAR(50) NOT NULL,
  `id_reservasi` INT(11) NOT NULL,
  `metode` VARCHAR(50) DEFAULT NULL,
  `jumlah_bayar` DOUBLE DEFAULT NULL,
  `status` VARCHAR(20) DEFAULT NULL,
  `tanggal_bayar` DATE DEFAULT CURDATE(),
  PRIMARY KEY (`id_pembayaran`),
  KEY `id_reservasi` (`id_reservasi`),
  CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_reservasi`) REFERENCES `reservasi` (`id_reservasi`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Struktur tabel: reservasi
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `reservasi` (
  `id_reservasi` INT(11) NOT NULL AUTO_INCREMENT,
  `id_user` INT(11) NOT NULL,
  `id_kamar` INT(11) NOT NULL,
  `check_in` DATE NOT NULL,
  `check_out` DATE NOT NULL,
  `status` VARCHAR(20) DEFAULT 'Dipesan',
  PRIMARY KEY (`id_reservasi`),
  KEY `id_user` (`id_user`),
  KEY `id_kamar` (`id_kamar`),
  CONSTRAINT `reservasi_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
  CONSTRAINT `reservasi_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data reservasi
INSERT INTO `reservasi` (`id_reservasi`, `id_user`, `id_kamar`, `check_in`, `check_out`, `status`) VALUES
(7, 3, 29, '2025-05-01', '2025-05-02', 'Dipesan');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
