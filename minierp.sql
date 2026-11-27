-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2026 at 03:11 AM
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
-- Database: `minierp`
--

-- --------------------------------------------------------

--
-- Table structure for table `procurement_hpp`
--

CREATE TABLE `procurement_hpp` (
  `id` bigint(20) NOT NULL,
  `harga_beli_total` decimal(15,2) NOT NULL,
  `hpp_per_unit` decimal(15,2) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `nama_item` varchar(100) NOT NULL,
  `tanggal_input` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `procurement_hpp`
--

INSERT INTO `procurement_hpp` (`id`, `harga_beli_total`, `hpp_per_unit`, `jumlah`, `nama_item`, `tanggal_input`) VALUES
(1, 35000.00, 7000.00, 5, 'Kitsune', '2026-11-13 01:48:44.000000'),
(2, 150000.00, 50000.00, 3, 'Dragon', '2026-11-26 21:57:37.000000');

-- --------------------------------------------------------

--
-- Table structure for table `redfinger_slots`
--

CREATE TABLE `redfinger_slots` (
  `id` bigint(20) NOT NULL,
  `akun_roblox` varchar(255) NOT NULL,
  `jam_selesai_joki` datetime(6) DEFAULT NULL,
  `nomor_aplikasi` int(11) DEFAULT NULL,
  `nomor_cloud` varchar(255) NOT NULL,
  `sisa_slot` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `redfinger_slots`
--

INSERT INTO `redfinger_slots` (`id`, `akun_roblox`, `jam_selesai_joki`, `nomor_aplikasi`, `nomor_cloud`, `sisa_slot`) VALUES
(1, 'RobloxPlayer_702', '2026-11-12 18:54:44.000000', 1, 'Cloud-1', 4),
(2, 'RobloxPlayer_630', '2026-11-13 03:50:30.000000', 1, 'Cloud-2', 4);

-- --------------------------------------------------------

--
-- Table structure for table `sales_transaction`
--

CREATE TABLE `sales_transaction` (
  `id` bigint(20) NOT NULL,
  `nama_item` varchar(100) NOT NULL,
  `jumlah_jual` int(11) NOT NULL,
  `harga_jual_total` decimal(15,2) NOT NULL,
  `hpp_saat_terjual` decimal(15,2) NOT NULL,
  `keuntungan` decimal(15,2) NOT NULL,
  `tanggal_jual` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_transaction`
--

INSERT INTO `sales_transaction` (`id`, `nama_item`, `jumlah_jual`, `harga_jual_total`, `hpp_saat_terjual`, `keuntungan`, `tanggal_jual`) VALUES
(1, 'Kitsune', 2, 700000.00, 14000.00, 686000.00, '2026-11-27 01:58:43.000000'),
(2, 'Kitsune', 1, 150000.00, 7000.00, 143000.00, '2026-11-27 01:59:13.000000');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('OWNER','WORKER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'agustino', 'agustino123', 'OWNER'),
(2, 'worker', 'worker123', 'WORKER'),
(3, 'owner', 'admin123', 'OWNER'),
(4, 'owner', 'admin123', 'OWNER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `procurement_hpp`
--
ALTER TABLE `procurement_hpp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `redfinger_slots`
--
ALTER TABLE `redfinger_slots`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_transaction`
--
ALTER TABLE `sales_transaction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `procurement_hpp`
--
ALTER TABLE `procurement_hpp`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `redfinger_slots`
--
ALTER TABLE `redfinger_slots`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sales_transaction`
--
ALTER TABLE `sales_transaction`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
