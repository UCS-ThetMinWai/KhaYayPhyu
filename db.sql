-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: khayayphyudb
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'CUSTOMER00000001','ACTIVE','Kyun Ywar',27,'Myo Thura','09684876787');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `buyAmount` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `saleAmount` int(11) DEFAULT NULL,
  `productId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7h9qoy6uptlnbi2yfj82h43ta` (`productId`),
  CONSTRAINT `FK_7h9qoy6uptlnbi2yfj82h43ta` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price`
--

LOCK TABLES `price` WRITE;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` VALUES (2,'PRICE00000001',NULL,500000,'2020-04-18',20,620000,NULL),(3,'PRICE00000002',NULL,400000,'2020-03-18',0,420000,1),(4,'PRICE00000003',NULL,500000,'2020-04-18',20,620000,NULL);
/*!40000 ALTER TABLE `price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `peckagingDate` datetime DEFAULT NULL,
  `peckagingType` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `currentPrice_id` bigint(20) DEFAULT NULL,
  `rawProduct_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5grdgvaeocgt364f3xgp35ycm` (`currentPrice_id`),
  KEY `FK_gwe4spa6dmushk3jlx1cv6y3k` (`rawProduct_id`),
  CONSTRAINT `FK_5grdgvaeocgt364f3xgp35ycm` FOREIGN KEY (`currentPrice_id`) REFERENCES `price` (`id`),
  CONSTRAINT `FK_gwe4spa6dmushk3jlx1cv6y3k` FOREIGN KEY (`rawProduct_id`) REFERENCES `rawproduct` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'PRODUCT00000001','OPEN','2020-04-13 00:00:00','PACK','CP CHICKEN FOOD',40,3,1),(2,'PRODUCT00000002','OPEN','2020-04-13 00:00:00','PACK','CP CHICKEN FOOD',40,3,1),(3,'PRODUCT00000003','OPEN',NULL,NULL,NULL,0,3,NULL),(4,'PRODUCT00000004','OPEN',NULL,NULL,NULL,0,3,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payAmount` int(11) DEFAULT NULL,
  `purchaseDate` date DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_btv65cbcqf0t6q00ax4hkml8h` (`customer_id`),
  KEY `FK_c1nf0krk9q9vlsgbttd6ahgwy` (`user_id`),
  CONSTRAINT `FK_btv65cbcqf0t6q00ax4hkml8h` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_c1nf0krk9q9vlsgbttd6ahgwy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaseorder`
--

DROP TABLE IF EXISTS `purchaseorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaseorder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `purchase_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dyaopsn2d9v24rpigsm4j257g` (`customer_id`),
  KEY `FK_c2sf6qjbdguewtkjclltb7p6e` (`product_id`),
  KEY `FK_knokq3uqmg2a48t0hlpmri9wg` (`purchase_id`),
  CONSTRAINT `FK_c2sf6qjbdguewtkjclltb7p6e` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_dyaopsn2d9v24rpigsm4j257g` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_knokq3uqmg2a48t0hlpmri9wg` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseorder`
--

LOCK TABLES `purchaseorder` WRITE;
/*!40000 ALTER TABLE `purchaseorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchaseorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rawproduct`
--

DROP TABLE IF EXISTS `rawproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rawproduct` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rawproduct`
--

LOCK TABLES `rawproduct` WRITE;
/*!40000 ALTER TABLE `rawproduct` DISABLE KEYS */;
INSERT INTO `rawproduct` VALUES (1,'RAWPRODUCT00000001','OPEN','FOOD','CHICKEN FOOD');
/*!40000 ALTER TABLE `rawproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payAmount` int(11) DEFAULT NULL,
  `saleDate` date DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `saleBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4eb1mq44mcjwilnepxayju8g` (`customer_id`),
  KEY `FK_r1pftf2fhm0mhd67d6o4vf4vc` (`saleBy_id`),
  CONSTRAINT `FK_4eb1mq44mcjwilnepxayju8g` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_r1pftf2fhm0mhd67d6o4vf4vc` FOREIGN KEY (`saleBy_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,'SALE00000001','OPEN',40000,'2020-04-13',50000,1,1),(2,'SALE00000002','OPEN',40000,'2020-04-13',50000,1,1),(3,'SALE00000003','OPEN',40000,'2020-04-13',50000,1,NULL);
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saleorder`
--

DROP TABLE IF EXISTS `saleorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saleorder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `peckagingType` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `sale` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m9hdvkhj5juxx9v003s8ylr9h` (`product_id`),
  KEY `FK_swyml43km22w3w6ch8cawm1e8` (`sale`),
  CONSTRAINT `FK_m9hdvkhj5juxx9v003s8ylr9h` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_swyml43km22w3w6ch8cawm1e8` FOREIGN KEY (`sale`) REFERENCES `sale` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saleorder`
--

LOCK TABLES `saleorder` WRITE;
/*!40000 ALTER TABLE `saleorder` DISABLE KEYS */;
INSERT INTO `saleorder` VALUES (1,'SALEORDER00000001',NULL,0,1,0,0,NULL,1),(2,'SALEORDER00000002',NULL,0,1,0,0,NULL,2),(3,'SALEORDER00000003',NULL,4000,1,12,20.5,1,2);
/*!40000 ALTER TABLE `saleorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'USER00000001','ACTIVE','Kyun Ywar',27,'U mg','09771443284','EMPLOYEE');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-02 23:50:14
