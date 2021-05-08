-- --------------------------------------------------------
-- 호스트:                          192.168.184.130
-- 서버 버전:                        10.2.13-MariaDB-10.2.13+maria~jessie - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- craw 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `craw` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `craw`;

-- 테이블 craw.lowest_price_queue 구조 내보내기
CREATE TABLE IF NOT EXISTS `lowest_price_queue` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `submit` tinyint(4) NOT NULL DEFAULT 0,
  `urlId` int(11) NOT NULL,
  `source` bigint(20) NOT NULL,
  `cursor` bigint(20) NOT NULL,
  `summary` mediumtext DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `submit` (`submit`),
  KEY `regDate` (`regDate`),
  KEY `urlId` (`urlId`),
  KEY `a` (`source`) USING BTREE,
  KEY `b` (`cursor`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 craw.parse 구조 내보내기
CREATE TABLE IF NOT EXISTS `parse` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `urlId` int(11) DEFAULT NULL,
  `workTime` datetime NOT NULL,
  `price` int(11) NOT NULL DEFAULT 0,
  `soldOut` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`seq`),
  KEY `urlId` (`urlId`),
  KEY `workTime` (`workTime`)
) ENGINE=InnoDB AUTO_INCREMENT=432 DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 craw.parse_attribute 구조 내보내기
CREATE TABLE IF NOT EXISTS `parse_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `psrseId` int(11) NOT NULL,
  `type` int(11) NOT NULL DEFAULT 1,
  `cardSalePercent` int(11) DEFAULT NULL,
  `prices` int(11) DEFAULT NULL,
  `etc` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=779 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 뷰 craw.parse_view 구조 내보내기
-- VIEW 종속성 오류를 극복하기 위해 임시 테이블을 생성합니다.
CREATE TABLE `parse_view` (
	`url` VARCHAR(500) NOT NULL COLLATE 'utf8mb4_bin',
	`seq` BIGINT(20) NOT NULL,
	`title` VARCHAR(300) NOT NULL COLLATE 'utf8mb4_bin',
	`urlId` INT(11) NULL,
	`workTime` DATETIME NOT NULL,
	`price` INT(11) NOT NULL,
	`soldOut` TINYINT(4) NOT NULL,
	`id` INT(11) NOT NULL,
	`psrseId` INT(11) NOT NULL,
	`type` INT(11) NOT NULL,
	`cardSalePercent` INT(11) NULL,
	`prices` INT(11) NULL,
	`etc` VARCHAR(100) NULL COLLATE 'utf8mb4_bin'
) ENGINE=MyISAM;

-- 테이블 craw.url 구조 내보내기
CREATE TABLE IF NOT EXISTS `url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT 'www.coupang.com',
  `url` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `note` text COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 craw.url_thumbnail 구조 내보내기
CREATE TABLE IF NOT EXISTS `url_thumbnail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `urlId` int(11) NOT NULL,
  `image` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 craw.url_watcher 구조 내보내기
CREATE TABLE IF NOT EXISTS `url_watcher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `urlId` int(11) NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT 'dlfdnd0725@gmail.com',
  `regDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 뷰 craw.parse_view 구조 내보내기
-- 임시 테이블을 제거하고 최종 VIEW 구조를 생성
DROP TABLE IF EXISTS `parse_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `parse_view` AS select `c`.`url` AS `url`,`a`.`seq` AS `seq`,`a`.`title` AS `title`,`a`.`urlId` AS `urlId`,`a`.`workTime` AS `workTime`,`a`.`price` AS `price`,`a`.`soldOut` AS `soldOut`,`b`.`id` AS `id`,`b`.`psrseId` AS `psrseId`,`b`.`type` AS `type`,`b`.`cardSalePercent` AS `cardSalePercent`,`b`.`prices` AS `prices`,`b`.`etc` AS `etc` from ((`parse` `a` join `parse_attribute` `b` on(`a`.`seq` = `b`.`psrseId`)) join `url` `c` on(`a`.`urlId` = `c`.`id`)) order by `a`.`seq` desc;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
