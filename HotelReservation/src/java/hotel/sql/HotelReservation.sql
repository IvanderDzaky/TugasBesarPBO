-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 27, 2025 at 11:20 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelreservation`
USE `hotelreservation`;
--

-- --------------------------------------------------------

--
-- Table structure for table `fasilitas`
--

CREATE TABLE `fasilitas` (
  `id_fasilitas` int(11) NOT NULL,
  `nama_fasilitas` varchar(100) NOT NULL,
  `deskripsi_fasilitas` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fasilitas`
--

INSERT INTO `fasilitas` (`id_fasilitas`, `nama_fasilitas`, `deskripsi_fasilitas`) VALUES
(1, 'WiFi', 'High speed WiFi quality with Indihome 500mb/s.'),
(2, 'AC', 'Most coldest Air Conditioner that ever had.'),
(3, 'TV', 'Smart Tv'),
(4, 'Shower', 'Water Heater, Bathub'),
(5, 'Cleaning', 'Cleaning Service'),
(22, 'Coffe', 'Premium Coffe'),
(25, 'Balcony', 'Premium Balcony');

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` int(11) NOT NULL,
  `nomor_kamar` varchar(10) NOT NULL,
  `tipe` varchar(50) NOT NULL,
  `harga` double NOT NULL,
  `status` tinyint(4) DEFAULT 1,
  `max_guest` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar`
--

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

--
-- Table structure for table `kamar_fasilitas`
--

CREATE TABLE `kamar_fasilitas` (
  `id_kamar` int(11) NOT NULL,
  `id_fasilitas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar_fasilitas`
--

INSERT INTO `kamar_fasilitas` (`id_kamar`, `id_fasilitas`) VALUES
(19, 1),
(19, 2),
(19, 3),
(19, 4),
(20, 1),
(20, 2),
(20, 3),
(20, 4),
(21, 1),
(21, 2),
(21, 3),
(21, 4),
(22, 1),
(22, 2),
(22, 3),
(22, 4),
(22, 22),
(23, 1),
(23, 2),
(23, 3),
(23, 4),
(23, 22),
(24, 1),
(24, 2),
(24, 3),
(24, 4),
(24, 5),
(24, 22),
(24, 25),
(25, 1),
(25, 2),
(25, 3),
(25, 4),
(25, 5),
(25, 22),
(25, 25),
(26, 1),
(26, 2),
(26, 3),
(26, 4),
(26, 5),
(26, 22),
(26, 25),
(27, 1),
(27, 2),
(27, 3),
(27, 4),
(27, 5),
(27, 22),
(27, 25),
(28, 1),
(28, 2),
(28, 3),
(28, 4),
(28, 5),
(28, 22),
(28, 25),
(29, 1),
(29, 2),
(29, 3),
(29, 4),
(29, 5),
(29, 22),
(29, 25);

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` varchar(50) NOT NULL,
  `id_reservasi` int(11) NOT NULL,
  `metode` varchar(50) DEFAULT NULL,
  `jumlah_bayar` double DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `tanggal_bayar` date DEFAULT NULL,
  `deadline` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `id_reservasi`, `metode`, `jumlah_bayar`, `status`, `tanggal_bayar`, `deadline`) VALUES
('PAY-1748335217971', 27, 'QRIS', 960, 'Dibatalkan', '2025-05-27', '2025-05-27 08:43:43'),
('PAY-1748335405460', 25, 'QRIS', 400, 'Dibatalkan', '2025-05-27', '2025-05-27 08:43:39'),
('PAY-1748336961945', 28, 'E-Wallet', 90, 'Pending', '2025-05-27', '2025-05-28 09:09:21'),
('PAY-1748337568039', 29, 'Transfer Bank', 3500, 'Dibatalkan', '2025-05-27', '2025-05-27 09:20:13'),
('PAY-1748337636298', 30, 'Transfer Bank', 300, 'Pending', NULL, '2025-05-28 09:20:36');

-- --------------------------------------------------------

--
-- Table structure for table `reservasi`
--

CREATE TABLE `reservasi` (
  `id_reservasi` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_kamar` int(11) NOT NULL,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `status` varchar(20) DEFAULT 'Dipesan'
) ;

--
-- Dumping data for table `reservasi`
--

INSERT INTO `reservasi` (`id_reservasi`, `id_user`, `id_kamar`, `check_in`, `check_out`, `status`) VALUES
(21, 23, 29, '2025-05-29', '2025-06-07', 'Dibatalkan'),
(22, 23, 29, '2025-05-30', '2025-06-07', 'Dibatalkan'),
(23, 38, 22, '2025-05-28', '2025-05-29', 'Dibatalkan'),
(24, 23, 29, '2025-05-29', '2025-05-30', 'Dipesan'),
(25, 38, 29, '2025-05-30', '2025-05-31', 'Dibatalkan'),
(26, 23, 24, '2025-05-30', '2025-05-31', 'Dipesan'),
(27, 38, 22, '2025-05-30', '2025-06-07', 'Dibatalkan'),
(28, 38, 19, '2025-05-30', '2025-05-31', 'Dipesan'),
(29, 39, 27, '2025-05-28', '2025-06-07', 'Dibatalkan'),
(30, 39, 25, '2025-05-30', '2025-05-31', 'Dipesan');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `isAdmin` tinyint(4) NOT NULL DEFAULT 0,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama`, `email`, `password`, `isAdmin`, `createdAt`) VALUES
(23, 'mahmud', 'mahmud@gmail.com', '$2a$10$OzakO5ImwJ70GzUGh09TrufpiEs/mN0QmSzfxZj4E0s/kMjSA2YHS', 0, '2025-05-19 02:30:43'),
(38, 'fattan', 'fattan@gmail.com', '$2a$10$Fn1HcptBuu3RlwMc08IO4eagqGylCrz72WAwg/PfgG62z1MR8hgky', 0, '2025-05-24 07:09:32'),
(39, 'NIKO', 'Kayvon@gmail.com', '$2a$10$i5fc7WiJeMG4npJoMXeQWO5Sr/lWWhwNVf6aAMOIuMQ5R2h9ztwtq', 0, '2025-05-24 10:30:14'),
(40, 'ADMIN', 'admin@yahoo.com', '$2a$10$.gefvWczpIRlk2bCW1BdYelovWNT1KsSbEqY5PdvUvDZZC7wQMVU6', 1, '2025-05-24 10:37:53');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fasilitas`
--
ALTER TABLE `fasilitas`
  ADD PRIMARY KEY (`id_fasilitas`),
  ADD UNIQUE KEY `nama_fasilitas` (`nama_fasilitas`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`),
  ADD UNIQUE KEY `nomor_kamar` (`nomor_kamar`);

--
-- Indexes for table `kamar_fasilitas`
--
ALTER TABLE `kamar_fasilitas`
  ADD PRIMARY KEY (`id_kamar`,`id_fasilitas`),
  ADD KEY `id_fasilitas` (`id_fasilitas`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `id_reservasi` (`id_reservasi`);

--
-- Indexes for table `reservasi`
--
ALTER TABLE `reservasi`
  ADD PRIMARY KEY (`id_reservasi`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `fasilitas`
--
ALTER TABLE `fasilitas`
  MODIFY `id_fasilitas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `kamar`
--
ALTER TABLE `kamar`
  MODIFY `id_kamar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `reservasi`
--
ALTER TABLE `reservasi`
  MODIFY `id_reservasi` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `kamar_fasilitas`
--
ALTER TABLE `kamar_fasilitas`
  ADD CONSTRAINT `kamar_fasilitas_ibfk_1` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE,
  ADD CONSTRAINT `kamar_fasilitas_ibfk_2` FOREIGN KEY (`id_fasilitas`) REFERENCES `fasilitas` (`id_fasilitas`) ON DELETE CASCADE;

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_reservasi`) REFERENCES `reservasi` (`id_reservasi`) ON DELETE CASCADE;

--
-- Constraints for table `reservasi`
--
ALTER TABLE `reservasi`
  ADD CONSTRAINT `reservasi_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservasi_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
