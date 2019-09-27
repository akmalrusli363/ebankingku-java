-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 15, 2019 at 05:55 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ebankingku`
--
CREATE DATABASE IF NOT EXISTS `ebankingku` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ebankingku`;

-- --------------------------------------------------------

--
-- Table structure for table `bank_customers`
--

CREATE TABLE `bank_customers` (
  `customer_id` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `saldo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bank_customers`
--

INSERT INTO `bank_customers` (`customer_id`, `fullname`, `username`, `password`, `saldo`) VALUES
(103, 'Andi Wiraka', 'AndiWiraka11', 'p4$sw0Rd_d0e@_kAt4', 145000),
(106, 'Leanna Leopard', 'LeannaLeopard78', 'j03$T_d0.iT', 2825000),
(107, 'Akong bejat', 'Akongbejat875', 'kakeksugiono', 6000000),
(108, 'Aku bokek', 'Akubokek434', 'gakadaduit', 150000),
(109, 'Keanu', 'KeanuKeanu620', 'bapakKau', 160000),
(110, 'Widya karin', 'Widyakarin371', 'yunus$alim<', 55000);

-- --------------------------------------------------------

--
-- Table structure for table `bank_transactions`
--

CREATE TABLE `bank_transactions` (
  `transaction_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date_processed` datetime NOT NULL,
  `preprocessed_saldo` bigint(20) NOT NULL,
  `processed_saldo` bigint(20) NOT NULL,
  `sisa_saldo` bigint(20) NOT NULL,
  `is_top_up` tinyint(1) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bank_transactions`
--

INSERT INTO `bank_transactions` (`transaction_id`, `customer_id`, `date_processed`, `preprocessed_saldo`, `processed_saldo`, `sisa_saldo`, `is_top_up`, `description`) VALUES
(1, 106, '2019-09-10 19:19:57', 1125000, 25000, 1100000, 1, ''),
(2, 106, '2019-09-10 19:22:57', 1100000, 25000, 1075000, 1, ''),
(3, 103, '2019-09-10 19:30:17', 0, 125000, 125000, 1, ''),
(4, 106, '2019-09-12 23:17:17', 1175000, 25000, 1150000, 1, ''),
(5, 106, '2019-09-12 23:24:31', 1150000, 25000, 1125000, 1, ''),
(6, 106, '2019-09-12 23:25:16', 1125000, 100000, 1225000, 1, 'Topup indomaret'),
(7, 106, '2019-09-12 23:33:40', 1225000, 100000, 1325000, 1, 'topup dari teman'),
(8, 106, '2019-09-12 23:33:59', 1325000, 200000, 1125000, 0, 'voucher game'),
(9, 106, '2019-09-13 11:43:51', 1125000, 1000000, 2125000, 1, 'uang bulanan'),
(10, 107, '2019-09-13 13:56:34', 8000000, 80000000, 88000000, 1, 'Duel sama vanesa angel'),
(11, 107, '2019-09-13 13:56:59', 88000000, 88000000, 0, 0, 'bayar nginap semalam sama vanesa angel'),
(12, 108, '2019-09-13 14:09:41', 0, 50000, 50000, 1, 'buat isi pulsa'),
(13, 107, '2019-09-13 14:11:04', 0, 12000000, 12000000, 1, 'duet sama vina garut'),
(14, 106, '2019-09-13 14:11:55', 2125000, 1000000, 3125000, 1, 'uang transport'),
(15, 106, '2019-09-13 14:12:31', 3125000, 300000, 2825000, 0, 'bayar utang'),
(16, 107, '2019-09-13 14:22:24', 12000000, 20000, 12020000, 1, 'kakek minta pulsa'),
(17, 107, '2019-09-13 14:22:42', 12020000, 1020000, 11000000, 0, 'bayarin pulsa vina'),
(18, 107, '2019-09-13 14:29:00', 11000000, 1000000, 12000000, 1, 'none'),
(19, 107, '2019-09-13 14:42:12', 12000000, 1000000, 11000000, 0, 'vina bayarin'),
(20, 108, '2019-09-13 14:43:23', 50000, 100000, 150000, 1, 'mobil legend pengen beli skin'),
(21, 103, '2019-09-13 14:45:02', 125000, 20000, 145000, 1, ''),
(22, 109, '2019-09-13 15:03:45', 60000, 100000, 160000, 1, 'dari mantan'),
(23, 107, '2019-09-14 10:46:19', 11000000, 5000000, 6000000, 0, 'bayarin lagi ke supplier vina'),
(24, 110, '2019-09-14 10:48:37', 20000, 35000, 55000, 1, 'tambahan saldo');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bank_customers`
--
ALTER TABLE `bank_customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `bank_transactions`
--
ALTER TABLE `bank_transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD UNIQUE KEY `transaction_id` (`transaction_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bank_transactions`
--
ALTER TABLE `bank_transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
