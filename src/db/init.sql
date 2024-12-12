-- MySQL dump 10.13  Distrib 9.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: arka_db
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `addressID` int NOT NULL AUTO_INCREMENT,
  `clientID` varchar(10) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `barangay` varchar(100) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`addressID`),
  KEY `fk_clientID_address` (`clientID`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `fk_clientID` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `fk_clientID_address` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`addressID`, `clientID`, `country`, `province`, `city`, `barangay`, `street`) VALUES (24,'BZKFC71622','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),(25,'OINRZ46347','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),(26,'IHNFY39071','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),(37,'RHOLC27667','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent` (
  `agentID` varchar(10) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`agentID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent`
--

LOCK TABLES `agent` WRITE;
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` (`agentID`, `username`, `password`) VALUES ('24-28346','tmmanuel','tmmanuel'),('24-29375','thalia','manueltm'),('24-69992','roshieleaila','roshiele'),('24-77691','ailaroshiele','roshiele');
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citizenship`
--

DROP TABLE IF EXISTS `citizenship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizenship` (
  `citizenshipID` int NOT NULL AUTO_INCREMENT,
  `clientID` varchar(10) DEFAULT NULL,
  `citizenship` varchar(100) DEFAULT NULL,
  `nationality` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`citizenshipID`),
  KEY `fk_clientID_citizenship` (`clientID`),
  CONSTRAINT `citizenship_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `fk_clientID_citizenship` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizenship`
--

LOCK TABLES `citizenship` WRITE;
/*!40000 ALTER TABLE `citizenship` DISABLE KEYS */;
INSERT INTO `citizenship` (`citizenshipID`, `clientID`, `citizenship`, `nationality`) VALUES (24,'BZKFC71622','Filipino','Filipino'),(25,'OINRZ46347','Filipino','Filipino'),(26,'IHNFY39071','Filipino','Filipino'),(37,'RHOLC27667','Filipino','Filipino');
/*!40000 ALTER TABLE `citizenship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `clientID` varchar(10) NOT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `middleName` varchar(100) DEFAULT NULL,
  `honorific` varchar(50) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `civilStatus` varchar(50) DEFAULT NULL,
  `placeOfBirth` varchar(100) DEFAULT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  `emailAddress` varchar(100) DEFAULT NULL,
  `occupation` varchar(100) DEFAULT NULL,
  `companyName` varchar(100) DEFAULT NULL,
  `agentID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `unique_email` (`emailAddress`),
  UNIQUE KEY `unique_contactNumber` (`contactNumber`),
  KEY `agentID` (`agentID`),
  CONSTRAINT `client_ibfk_1` FOREIGN KEY (`agentID`) REFERENCES `agent` (`agentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`clientID`, `lastName`, `firstName`, `middleName`, `honorific`, `sex`, `dateOfBirth`, `civilStatus`, `placeOfBirth`, `contactNumber`, `emailAddress`, `occupation`, `companyName`, `agentID`) VALUES ('BZKFC71622','Donayre','Rosemarie','Carag','Mrs.','Female','1970-06-03','Widowed','San Juan, Batangas','09988584015','rosemariedonayre90@gmail.com','Landlady','N/A','24-77691'),('IHNFY39071','Donayre','Aila Roshiele','Carag','Ms.','Female','2005-03-25','Single','Batangas City, Batangas','09452971404','ailaroshieledonayre@gmail.com','Student','N/A','24-69992'),('OINRZ46347','Manuel','Shiela May','Donayre','Mrs.','Female','1992-06-07','Married','Batangas City, Batangas','09618412073','shieladonayre@gmail.com','Landlady','N/A','24-69992'),('RHOLC27667','Donayre','Aljon Mart Kenneth','Carag','Mr.','Male','1990-07-22','Single','Batangas City, Batangas','09937428524','amkdonayre@gmail.com','OFW','ADNOC','24-77691');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income`
--

DROP TABLE IF EXISTS `income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `income` (
  `incomeID` int NOT NULL AUTO_INCREMENT,
  `clientID` varchar(10) DEFAULT NULL,
  `sourceIncome` varchar(100) DEFAULT NULL,
  `annualIncome` int DEFAULT NULL,
  PRIMARY KEY (`incomeID`),
  KEY `fk_clientID_income` (`clientID`),
  CONSTRAINT `fk_clientID_income` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `income_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income`
--

LOCK TABLES `income` WRITE;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
INSERT INTO `income` (`incomeID`, `clientID`, `sourceIncome`, `annualIncome`) VALUES (23,'BZKFC71622','Business',240000),(24,'OINRZ46347','Business',300000),(25,'IHNFY39071','Scholarships',50000),(36,'RHOLC27667','Salary',500000);
/*!40000 ALTER TABLE `income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `paymentID` int NOT NULL AUTO_INCREMENT,
  `clientID` varchar(10) DEFAULT NULL,
  `policyID` varchar(20) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentAmount` decimal(10,2) DEFAULT NULL,
  `nextPayment` date DEFAULT NULL,
  `lastPayment` date DEFAULT NULL,
  PRIMARY KEY (`paymentID`),
  KEY `clientID` (`clientID`),
  KEY `policyID` (`policyID`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`policyID`) REFERENCES `policy` (`policyID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8499083 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` (`paymentID`, `clientID`, `policyID`, `paymentDate`, `paymentAmount`, `nextPayment`, `lastPayment`) VALUES (3939045,'OINRZ46347','A56049KE','2024-11-17',270000.00,'2025-11-17','2034-11-17'),(5238994,'RHOLC27667','A99863DI','2024-11-19',142500.00,'2025-11-19','2044-11-19'),(8135134,'IHNFY39071','A32909AL','2024-11-17',7000.00,'2025-11-17','2059-11-17'),(8499082,'BZKFC71622','A44870PF','2024-11-17',270000.00,'2025-11-17','2034-11-17');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `policy`
--

DROP TABLE IF EXISTS `policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `policy` (
  `policyID` varchar(20) NOT NULL,
  `clientID` varchar(20) DEFAULT NULL,
  `policyType` varchar(50) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `premiumAmount` decimal(15,2) DEFAULT NULL,
  `coverageAmount` decimal(15,2) DEFAULT NULL,
  `paymentPeriod` int DEFAULT NULL,
  `paymentFrequency` varchar(100) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
  `beneficiaryName` varchar(255) DEFAULT NULL,
  `beneficiaryRelationship` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`policyID`),
  KEY `fk_clientID_policy` (`clientID`),
  CONSTRAINT `fk_clientID_policy` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `policy_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `policy`
--

LOCK TABLES `policy` WRITE;
/*!40000 ALTER TABLE `policy` DISABLE KEYS */;
INSERT INTO `policy` (`policyID`, `clientID`, `policyType`, `startDate`, `endDate`, `premiumAmount`, `coverageAmount`, `paymentPeriod`, `paymentFrequency`, `status`, `beneficiaryName`, `beneficiaryRelationship`) VALUES ('A32909AL','IHNFY39071','Tanso Plan','2024-11-17','2106-03-24',7000.00,250000.00,35,'Annually','ACTIVE','Rosemarie C. Donayre','Mother'),('A44870PF','BZKFC71622','Ginto Plan','2024-11-17','2071-06-02',270000.00,3000000.00,10,'Annually','ACTIVE','Aila Roshiele C. Donayre','Daughter'),('A56049KE','OINRZ46347','Ginto Plan','2024-11-17','2093-06-06',270000.00,3000000.00,10,'Annually','ACTIVE','Thalia D. Manuel','Daughter'),('A99863DI','RHOLC27667','Ginto Plan','2024-11-19','2091-07-21',142500.00,3000000.00,20,'Annually','ACTIVE','Rosemarie C. Donayre','Mother');
/*!40000 ALTER TABLE `policy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-12 16:11:29
