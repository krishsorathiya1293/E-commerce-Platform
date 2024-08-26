-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 05, 2023 at 01:26 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecommerce`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetElectronics` (IN `name1` VARCHAR(20))   BEGIN
select * from electronics_items WHERE Name=name1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetElectronicsId` (IN `name1` VARCHAR(20), IN `name2` VARCHAR(20))   BEGIN
SELECT * FROM electronics_items WHERE Name=name1 AND Brand=name2;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Getgrossary` (IN `name1` VARCHAR(20))   BEGIN
SELECT * from grossary WHERE Name=name1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetGrossaryId` (IN `name1` VARCHAR(20))   BEGIN
SELECT * FROM grossary WHERE Name=name1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPersonalCare` (IN `name1` VARCHAR(20))   BEGIN
SELECT * FROM personal_care WHERE name=name1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPersonalCareId` (IN `name1` VARCHAR(20), IN `name2` VARCHAR(20))   BEGIN
SELECT * FROM  personal_care WHERE Name=name1 AND Brand=name2;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSellDetails` (IN `id` INT(10), IN `c` VARCHAR(20))   BEGIN
SELECT Id,sum(Quantity),Price FROM sell GROUP BY Id;
End$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `electronics_items`
--

CREATE TABLE `electronics_items` (
  `Id` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Brand` varchar(50) NOT NULL,
  `Discription` varchar(600) NOT NULL,
  `Stock` int(20) NOT NULL,
  `UnitPrice` double NOT NULL,
  `Type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `electronics_items`
--

INSERT INTO `electronics_items` (`Id`, `Name`, `Brand`, `Discription`, `Stock`, `UnitPrice`, `Type`) VALUES
(1, 'Smartphone', 'Apple', 'The Apple iPhone is known for its sleek design, high-quality camera, and seamless integration with the iOS ecosystem, offering a premium mobile experience.', 20, 75000, 'Electronics'),
(2, 'Laptop', 'Dell', 'i7,Rem-16gb,Rom-512gb,Dell laptops are recognized for their reliability and performance. They come in various models, catering to both business and personal computing needs.', 5, 67000, 'Electronics'),
(3, 'Television', 'Samsung', 'Samsung TVs boast vibrant displays and innovative features, such as QLED technology and smart functionality, providing an immersive entertainment experience.', 6, 21000, 'Electronics'),
(4, 'Tablet', 'Samsung', 'Samsung Galaxy Tab series offers a range of tablets with crisp screens, powerful processors, and S Pen support, ideal for work and entertainment on the go.', 6, 10000, 'Electronics'),
(5, 'Wireless Earbuds', 'Apple', 'Apple AirPods are popular wireless earbuds known for their seamless connectivity with Apple devices, long battery life, and high-quality sound.', 20, 12000, 'Electronics'),
(6, 'Gaming Laptop', 'Asus', '16GB REM,1TB ROM,4GB GRAFIX CARD,ASUS ROG laptops are designed for gamers, featuring powerful hardware, high refresh rate displays, and RGB lighting for an immersive gaming experience.', 6, 85000, 'Electronics');

-- --------------------------------------------------------

--
-- Table structure for table `grossary`
--

CREATE TABLE `grossary` (
  `Id` int(10) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Price` double NOT NULL,
  `Quantity` int(10) NOT NULL,
  `Categorie` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `grossary`
--

INSERT INTO `grossary` (`Id`, `Name`, `Price`, `Quantity`, `Categorie`) VALUES
(1, 'wheat', 30, 88, 'Grossary'),
(2, 'Rice', 20, 90, 'Grossary'),
(3, 'salt', 15, 91, 'Grossary'),
(4, 'sugar', 30, 100, 'Grossary'),
(5, 'shampoo', 150, 50, 'Grossary'),
(6, 'soap', 20, 50, 'Grossary'),
(7, 'Toothpaste', 100, 50, 'Grossary'),
(8, 'Toothbrushes', 70, 50, 'Grossary'),
(9, 'hand sanatizer', 35, 100, 'Grossary'),
(19, 'AA', 3, 100, 'Grossary');

-- --------------------------------------------------------

--
-- Table structure for table `personal_care`
--

CREATE TABLE `personal_care` (
  `Id` int(20) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Brand` varchar(20) NOT NULL,
  `Quantity` varchar(20) NOT NULL,
  `UnitPrice` double NOT NULL,
  `Stock` int(10) NOT NULL,
  `Categorie` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `personal_care`
--

INSERT INTO `personal_care` (`Id`, `Name`, `Brand`, `Quantity`, `UnitPrice`, `Stock`, `Categorie`) VALUES
(1, 'Toothpaste', 'Colgate', '100 gm', 60, 28, 'personalCare'),
(2, 'Shampoo', 'Dove', '100 ml', 80, 50, 'personalCare'),
(3, 'Moisturizer', 'Himaliya', '100 ml', 120, 50, 'personalCare'),
(4, 'Hair Oil', 'mamaearth', '125 ml', 125, 30, 'personalCare'),
(5, 'Conditioner', 'Dove', '50 ml', 30, 50, 'personalCare'),
(6, 'Hair Color', 'Godrej', '30 gm', 60, 20, 'personalCare'),
(7, 'Face Wash', 'joy', '75 gm', 110, 25, 'personalCare'),
(8, 'Beard oil', 'joy', '25 ml', 100, 20, 'personalCare');

-- --------------------------------------------------------

--
-- Table structure for table `sell`
--

CREATE TABLE `sell` (
  `Id` int(10) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Price` double NOT NULL,
  `Quantity` int(10) NOT NULL,
  `Categories` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sell`
--

INSERT INTO `sell` (`Id`, `Name`, `Price`, `Quantity`, `Categories`) VALUES
(10, 'wheat', 30, 0, 'Grossary'),
(2, 'Laptop', 67000, 1, 'Electronics'),
(2, 'Laptop', 67000, 0, 'Electronics'),
(1, 'Toothpaste', 60, 1, 'personalCare'),
(10, 'wheat', 30, 1, 'Grossary'),
(2, 'Laptop', 67000, 0, 'Electronics'),
(1, 'Toothpaste', 60, 0, 'personalCare'),
(1, 'Toothpaste', 60, 0, 'personalCare'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(2, 'Laptop', 67000, 1, 'Electronics'),
(1, 'wheat', 30, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Laptop', 67000, 1, 'Electronics'),
(1, 'Toothpaste', 60, 1, 'personalCare'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary'),
(1, 'wheat', 30, 1, 'Grossary'),
(2, 'Rice', 20, 1, 'Grossary'),
(3, 'salt', 15, 1, 'Grossary');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `electronics_items`
--
ALTER TABLE `electronics_items`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `grossary`
--
ALTER TABLE `grossary`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `personal_care`
--
ALTER TABLE `personal_care`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `electronics_items`
--
ALTER TABLE `electronics_items`
  MODIFY `Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `grossary`
--
ALTER TABLE `grossary`
  MODIFY `Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `personal_care`
--
ALTER TABLE `personal_care`
  MODIFY `Id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
