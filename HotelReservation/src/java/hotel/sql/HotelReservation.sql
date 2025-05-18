-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 18, 2025 at 09:49 AM
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
--

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` int(11) NOT NULL,
  `nomor_kamar` varchar(10) NOT NULL,
  `tipe` varchar(50) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `nomor_kamar`, `tipe`, `harga`, `status`) VALUES
(1, '101', 'Single Room', 250000.00, 0),
(2, '102', 'Family Room', 350000.00, 0),
(3, '201', 'Suite Room', 500000.00, 0);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(17, 'ADMIN', 'admin@yahoo.com', 'admin123', 1, '2025-04-30 17:00:00'),
(20, 'MAHMUD', 'mahmud@gmail.com', '123', 0, '2025-05-01 17:00:00'),
(22, 'jawa', 'jawa@gmail.com', '123', 0, '2025-05-16 17:26:53'),
(23, 'kanjut', 'kanjut@mail.com', '123', 0, '2025-05-17 06:55:26'),
(26, 'kanjut', 'memeg@maik', '123', 0, '2025-05-17 07:02:31'),
(31, 'jawir', 'jawir@yahoo.com', '123', 0, '2025-05-17 07:18:33'),
(36, 'kanjut', 'adas2@12', '123', 0, '2025-05-17 07:30:08'),
(40, 'jawa', 'asdsa@12312321', '123', 0, '2025-05-17 07:34:59'),
(41, 'kanjut', 'adasdsaasdad@222', '123', 0, '2025-05-17 07:37:05'),
(42, 'kanjut', 'memeg@222', '123', 0, '2025-05-17 07:37:15'),
(43, 'xx knjut', 'keenan@22', '123', 0, '2025-05-17 07:39:04'),
(44, 'fater', 'fater@mail.com', '123', 0, '2025-05-17 07:41:22'),
(45, 'adsada', 'asdasdaq232123@1231', '123', 0, '2025-05-17 07:44:08'),
(46, 'tolol', 'tolol123@123', '123', 0, '2025-05-18 07:01:31'),
(47, 'jok', 'jokowi@123', '123', 0, '2025-05-18 07:02:21'),
(54, 'tes', 'bapakkau@maikl', 'admin123', 0, '2025-05-18 07:11:39');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`),
  ADD UNIQUE KEY `nomor_kamar` (`nomor_kamar`);

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
  ADD UNIQUE KEY `unique_email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kamar`
--
ALTER TABLE `kamar`
  MODIFY `id_kamar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `reservasi`
--
ALTER TABLE `reservasi`
  MODIFY `id_reservasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- Constraints for dumped tables
--

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
