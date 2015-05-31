-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2015 at 05:04 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `arrive_date` datetime DEFAULT NULL,
  `leave_date` datetime DEFAULT NULL,
  `customer_name` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `identity_number` varchar(20) DEFAULT NULL,
  `region` varchar(30) DEFAULT NULL,
  `paid` int(11) DEFAULT '0',
  `check_in` int(11) NOT NULL DEFAULT '0',
  `description` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `room_type` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `booked` int(11) DEFAULT '0',
  `check_in` int(11) NOT NULL DEFAULT '0',
  `arrive_date` datetime DEFAULT NULL,
  `leave_date` datetime DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `image` varchar(30) DEFAULT NULL,
  `description` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`id`, `name`, `room_type`, `capacity`, `price`, `booked`, `check_in`, `arrive_date`, `leave_date`, `book_id`, `image`, `description`, `created_at`, `updated_at`) VALUES
(1, 'Phòng 101', 1, 2, 400000, 0, 0, NULL, NULL, NULL, 'standard.png', 'Diện tích: 20 m2\n\nTRANG THIẾT BỊ PHÒNG NGỦ: \nMáy lạnh, truyền hình vệ tinh, điện thoại quốc tế, két an toàn, mini-bar, đông hồ báo thức, nước nóng, máy sấy tóc, bình nấu nước, 2 chai nước miễn phí', '2015-05-23 11:01:25', '2015-05-23 11:01:25'),
(2, 'Phòng 102', 1, 1, 400000, 0, 0, NULL, NULL, NULL, 'standard.png', 'Diện tích: 20 m2\n\nTRANG THIẾT BỊ PHÒNG NGỦ: \nMáy lạnh, truyền hình vệ tinh, điện thoại quốc tế, két an toàn, mini-bar, đông hồ báo thức, nước nóng, máy sấy tóc, bình nấu nước, 2 chai nước miễn phí', '2015-05-23 11:01:25', '2015-05-23 11:01:25'),
(3, 'Phòng 201', 2, 4, 800000, 0, 0, NULL, NULL, NULL, 'super.png', 'Diện tích: 28 - 32 m2\n\nTRANG THIẾT BỊ PHÒNG NGỦ: \nMáy lạnh, truyền hình vệ tinh, điện thoại quốc tế, két an toàn, mini-bar, nước nóng, đồng hồ báo thức, máy sấy tóc, bồn tắm, bình nấu nước, 2 chai nước miễn phí, sử dụng Internet ADSL & Wifi miễn phí.', '2015-05-27 15:19:56', '2015-05-27 15:19:56'),
(4, 'Phòng 202', 2, 4, 800000, 0, 0, NULL, NULL, NULL, 'super.png', 'Diện tích: 28 - 32 m2\n\nTRANG THIẾT BỊ PHÒNG NGỦ: \nMáy lạnh, truyền hình vệ tinh, điện thoại quốc tế, két an toàn, mini-bar, nước nóng, đồng hồ báo thức, máy sấy tóc, bồn tắm, bình nấu nước, 2 chai nước miễn phí, sử dụng Internet ADSL & Wifi miễn phí.', '2015-05-27 15:19:56', '2015-05-27 15:19:56'),
(5, 'Phòng 301', 3, 2, 1200000, 0, 0, NULL, NULL, NULL, 'vips.png', 'Diện tích: 64m2\n\nTRANG THIẾT BỊ PHÒNG NGỦ\nMáy lạnh, truyền hình vệ tinh, điện thoại quốc tế, két an toàn, phòng tiếp khách, mini-bar, nước nóng, đồng hồ báo thức, máy sấy tóc, bình nấu nước, bồn tắm massage, hoa tươi và trái cây đặt phòng ngày đầu tiên, sử dụng Internet ADSL & Wifi miễn phí.', '2015-05-27 23:43:57', '2015-05-27 23:43:57');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` int(11) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
