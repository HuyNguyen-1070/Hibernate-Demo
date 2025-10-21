-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 21, 2025 at 05:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `productdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `LanguageID` char(2) NOT NULL,
  `Language` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`LanguageID`, `Language`) VALUES
('en', 'English'),
('fr', 'French'),
('vi', 'Vietnamese');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ProductID` int(11) NOT NULL,
  `Price` decimal(19,4) NOT NULL,
  `Weight` decimal(6,2) NOT NULL,
  `ProductCategoryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ProductID`, `Price`, `Weight`, `ProductCategoryID`) VALUES
(102, 129.5000, 1.50, 1),
(201, 19.9900, 0.40, 2),
(301, 49.0000, 0.00, 3);

-- --------------------------------------------------------

--
-- Table structure for table `productcategory`
--

CREATE TABLE `productcategory` (
  `ProductCategoryID` int(11) NOT NULL,
  `CanBeShipped` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productcategory`
--

INSERT INTO `productcategory` (`ProductCategoryID`, `CanBeShipped`) VALUES
(1, 1),
(2, 1),
(3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `productcategorytranslation`
--

CREATE TABLE `productcategorytranslation` (
  `ProductCategoryID` int(11) NOT NULL,
  `LanguageID` char(2) NOT NULL,
  `CategoryName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productcategorytranslation`
--

INSERT INTO `productcategorytranslation` (`ProductCategoryID`, `LanguageID`, `CategoryName`) VALUES
(1, 'en', 'Electronics'),
(1, 'fr', 'Électronique'),
(1, 'vi', 'Điện tử'),
(2, 'en', 'Books'),
(2, 'vi', 'Sách'),
(3, 'en', 'Services'),
(3, 'vi', 'Dịch vụ');

-- --------------------------------------------------------

--
-- Table structure for table `producttranslation`
--

CREATE TABLE `producttranslation` (
  `ProductID` int(11) NOT NULL,
  `LanguageID` char(2) NOT NULL,
  `ProductName` varchar(100) NOT NULL,
  `ProductDescription` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `producttranslation`
--

INSERT INTO `producttranslation` (`ProductID`, `LanguageID`, `ProductName`, `ProductDescription`) VALUES
(102, 'en', 'Tablet Y', 'Lightweight and portable tablet for work and entertainment.'),
(102, 'FR', 'Tablette Y', 'Tablette légère et portable pour le travail et le divertissement.'),
(102, 'vi', 'Máy tính bảng Y', 'Máy tính bảng nhẹ và di động để làm việc và giải trí.'),
(201, 'en', 'The Lost City', 'An exciting adventure novel.'),
(201, 'fr', 'La Cité Perdue', 'Un roman d\'aventure passionnant.'),
(201, 'vi', 'Thành phố thất lạc', 'Một cuốn tiểu thuyết phiêu lưu thú vị.'),
(301, 'en', 'Software Support Package', '30-day premium support.'),
(301, 'vi', 'Gói hỗ trợ phần mềm', 'Hỗ trợ cao cấp trong 30 ngày.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`LanguageID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `fk_Product_ProductCategory` (`ProductCategoryID`);

--
-- Indexes for table `productcategory`
--
ALTER TABLE `productcategory`
  ADD PRIMARY KEY (`ProductCategoryID`);

--
-- Indexes for table `productcategorytranslation`
--
ALTER TABLE `productcategorytranslation`
  ADD PRIMARY KEY (`ProductCategoryID`,`LanguageID`),
  ADD KEY `fk_ProductCategoryTranslation_Language` (`LanguageID`);

--
-- Indexes for table `producttranslation`
--
ALTER TABLE `producttranslation`
  ADD PRIMARY KEY (`ProductID`,`LanguageID`),
  ADD KEY `fk_ProductTranslation_Language` (`LanguageID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=303;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_Product_ProductCategory` FOREIGN KEY (`ProductCategoryID`) REFERENCES `productcategory` (`ProductCategoryID`) ON UPDATE CASCADE;

--
-- Constraints for table `productcategorytranslation`
--
ALTER TABLE `productcategorytranslation`
  ADD CONSTRAINT `fk_ProductCategoryTranslation_Language` FOREIGN KEY (`LanguageID`) REFERENCES `language` (`LanguageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ProductCategoryTranslation_ProductCategory` FOREIGN KEY (`ProductCategoryID`) REFERENCES `productcategory` (`ProductCategoryID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `producttranslation`
--
ALTER TABLE `producttranslation`
  ADD CONSTRAINT `fk_ProductTranslation_Language` FOREIGN KEY (`LanguageID`) REFERENCES `language` (`LanguageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ProductTranslation_Product` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
