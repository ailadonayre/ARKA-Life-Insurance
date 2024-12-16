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
  `clientID` varchar(10) NOT NULL,
  `country` varchar(100) NOT NULL,
  `province` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `barangay` varchar(100) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`clientID`),
  CONSTRAINT `fk_clientID_address` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`clientID`, `country`, `province`, `city`, `barangay`, `street`) VALUES ('BZKFC71622','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),('CSOSK16700','Philippines','Cavite','Cavite City','Barangay 8','127 Street'),('IHNFY39071','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),('LRKIR84960','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),('MAOMY09436','Philippines','Batangas','Lipa City','San Carlos','Delta Street'),('OINRZ46347','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),('RHOLC27667','Philippines','Batangas','Batangas City','Kumintang Ibaba','Sterling Street'),('ZZDBE13965','Philippines','Batangas','Batangas City','Alangilan','Street 143');
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
INSERT INTO `agent` (`agentID`, `username`, `password`) VALUES ('24-09506','jaehyun','jaehyunjeong'),('24-14316','test','testpass'),('24-28346','tmmanuel','tmmanuel'),('24-29375','thalia','manueltm'),('24-66763','demo','demopass'),('24-69992','roshieleaila','roshiele'),('24-76167','donayre','donayreaila'),('24-77691','ailaroshiele','roshiele');
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citizenship`
--

DROP TABLE IF EXISTS `citizenship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizenship` (
  `clientID` varchar(10) NOT NULL,
  `citizenship` varchar(100) NOT NULL,
  `nationality` varchar(100) NOT NULL,
  PRIMARY KEY (`clientID`),
  CONSTRAINT `fk_clientID_citizenship` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizenship`
--

LOCK TABLES `citizenship` WRITE;
/*!40000 ALTER TABLE `citizenship` DISABLE KEYS */;
INSERT INTO `citizenship` (`clientID`, `citizenship`, `nationality`) VALUES ('BZKFC71622','Filipino','Filipino'),('CSOSK16700','Filipino-Korean','South Korean'),('IHNFY39071','Filipino','Filipino'),('LRKIR84960','Filipino','Filipino'),('MAOMY09436','Filipino','Filipino'),('OINRZ46347','Filipino','Filipino'),('RHOLC27667','Filipino','Filipino'),('ZZDBE13965','Filipino','Filipino');
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
  `agentID` varchar(10) NOT NULL DEFAULT '',
  `lastName` varchar(100) NOT NULL DEFAULT '',
  `firstName` varchar(100) NOT NULL DEFAULT '',
  `middleName` varchar(100) DEFAULT NULL,
  `honorific` varchar(50) NOT NULL DEFAULT '',
  `sex` varchar(10) NOT NULL DEFAULT '',
  `dateOfBirth` date DEFAULT NULL,
  `civilStatus` varchar(50) NOT NULL DEFAULT '',
  `placeOfBirth` varchar(100) NOT NULL DEFAULT '',
  `contactNumber` varchar(15) NOT NULL DEFAULT '',
  `emailAddress` varchar(100) NOT NULL DEFAULT '',
  `occupation` varchar(100) NOT NULL DEFAULT '',
  `companyName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `unique_contactNumber` (`contactNumber`),
  UNIQUE KEY `unique_email` (`emailAddress`),
  KEY `client_ibfk_1` (`agentID`),
  CONSTRAINT `client_ibfk_1` FOREIGN KEY (`agentID`) REFERENCES `agent` (`agentID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`clientID`, `agentID`, `lastName`, `firstName`, `middleName`, `honorific`, `sex`, `dateOfBirth`, `civilStatus`, `placeOfBirth`, `contactNumber`, `emailAddress`, `occupation`, `companyName`) VALUES ('BZKFC71622','24-77691','Donayre','Rosemarie','Carag','Mrs.','Female','1970-06-03','Widowed','San Juan, Batangas','09988584015','rosemariedonayre90@gmail.com','Landlady','N/A'),('CSOSK16700','24-14316','Lee','Mark','','Mr.','Male','1999-08-02','Single','Cavite City, Cavite','09834573012','marklee@gmail.com','Song Producer','SM Entertainment'),('IHNFY39071','24-69992','Donayre','Aila Roshiele','Carag','Ms.','Female','2005-03-25','Single','Batangas City, Batangas','09452971404','ailaroshieledonayre@gmail.com','Student','N/A'),('LRKIR84960','24-66763','Manuel','Thalia','Donayre','Ms.','Female','2007-07-27','Single','Batangas City, Batangas','09458710342','thaliamanuel@gmail.com','Student','N/A'),('MAOMY09436','24-76167','Chavez','Ana','Javier','Ms.','Female','1987-12-31','Single','Lipa City','09926894043','chavezanaj@gmail.com','Hairdresser','Elitista Salon'),('OINRZ46347','24-69992','Manuel','Shiela May','Donayre','Mrs.','Female','1992-06-07','Married','Batangas City, Batangas','09618412073','shieladonayre@gmail.com','Landlady','N/A'),('RHOLC27667','24-77691','Donayre','Aljon Mart Kenneth','Carag','Mr.','Male','1990-07-22','Single','Batangas City, Batangas','09937428524','amkdonayre@gmail.com','OFW','ADNOC'),('ZZDBE13965','24-76167','Perez','Kent Benedict','Ramos','Mr.','Male','2005-09-08','Single','Batangas City','09611402462','kentperez@gmail.com','Student','N/A');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income`
--

DROP TABLE IF EXISTS `income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `income` (
  `clientID` varchar(10) NOT NULL,
  `sourceIncome` varchar(100) NOT NULL,
  `annualIncome` int NOT NULL,
  PRIMARY KEY (`clientID`),
  CONSTRAINT `fk_clientID_income` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income`
--

LOCK TABLES `income` WRITE;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
INSERT INTO `income` (`clientID`, `sourceIncome`, `annualIncome`) VALUES ('BZKFC71622','Business',240000),('CSOSK16700','Salary',9000000),('IHNFY39071','Scholarships',50000),('LRKIR84960','Scholarships',84000),('MAOMY09436','Salary',150000),('OINRZ46347','Business',300000),('RHOLC27667','Salary',500000),('ZZDBE13965','Student Allowance',75000);
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
  `clientID` varchar(10) NOT NULL,
  `agentID` varchar(10) DEFAULT NULL,
  `policyID` varchar(20) NOT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentAmount` decimal(15,2) NOT NULL,
  `nextPayment` date DEFAULT NULL,
  `lastPayment` date DEFAULT NULL,
  PRIMARY KEY (`paymentID`),
  KEY `clientID` (`clientID`),
  KEY `policyID` (`policyID`),
  KEY `fk_payment_agentID` (`agentID`),
  CONSTRAINT `fk_payment_agentID` FOREIGN KEY (`agentID`) REFERENCES `agent` (`agentID`) ON DELETE CASCADE,
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`policyID`) REFERENCES `policy` (`policyID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8499083 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` (`paymentID`, `clientID`, `agentID`, `policyID`, `paymentDate`, `paymentAmount`, `nextPayment`, `lastPayment`) VALUES (822284,'CSOSK16700',NULL,'A88522HN','2024-12-15',96000.00,'2025-12-15','2034-12-15'),(1799126,'MAOMY09436','24-76167','A23600KC','2024-12-12',28000.00,'2025-12-12','2059-12-12'),(3615445,'LRKIR84960','24-66763','A36656BO','2024-12-12',28000.00,'2025-12-12','2059-12-12'),(3939045,'OINRZ46347','24-69992','A56049KE','2024-11-17',270000.00,'2025-11-17','2034-11-17'),(5238994,'RHOLC27667','24-77691','A99863DI','2024-11-19',142500.00,'2025-11-19','2044-11-19'),(6523942,'ZZDBE13965','24-76167','A32038UC','2024-12-12',11500.00,'2025-12-12','2044-12-12'),(8135134,'IHNFY39071','24-69992','A32909AL','2024-11-17',7000.00,'2025-11-17','2059-11-17'),(8499082,'BZKFC71622','24-77691','A44870PF','2024-11-17',270000.00,'2025-11-17','2034-11-17');
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
  `agentID` varchar(10) DEFAULT NULL,
  `policyType` varchar(50) NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `premiumAmount` decimal(15,2) NOT NULL,
  `coverageAmount` decimal(15,2) NOT NULL,
  `paymentPeriod` int NOT NULL,
  `paymentFrequency` varchar(50) NOT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
  `beneficiaryName` varchar(255) DEFAULT NULL,
  `beneficiaryRelationship` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`policyID`),
  UNIQUE KEY `unique_clientID` (`clientID`),
  KEY `fk_policy_agentID` (`agentID`),
  CONSTRAINT `fk_clientID_policy` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  CONSTRAINT `fk_policy_agentID` FOREIGN KEY (`agentID`) REFERENCES `agent` (`agentID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `policy`
--

LOCK TABLES `policy` WRITE;
/*!40000 ALTER TABLE `policy` DISABLE KEYS */;
INSERT INTO `policy` (`policyID`, `clientID`, `agentID`, `policyType`, `startDate`, `endDate`, `premiumAmount`, `coverageAmount`, `paymentPeriod`, `paymentFrequency`, `status`, `beneficiaryName`, `beneficiaryRelationship`) VALUES ('A23600KC','MAOMY09436','24-76167','Pilak Plan','2024-12-12','2088-12-30',28000.00,1000000.00,35,'Annually','ACTIVE','Severino Chavez','Father'),('A32038UC','ZZDBE13965','24-76167','Tanso Plan','2024-12-12','2106-09-07',11500.00,250000.00,20,'Annually','ACTIVE','Lorna Perez','Mother'),('A32909AL','IHNFY39071','24-69992','Tanso Plan','2024-11-17','2106-03-24',7000.00,250000.00,35,'Annually','ACTIVE','Rosemarie C. Donayre','Mother'),('A36656BO','LRKIR84960','24-66763','Pilak Plan','2024-12-12','2108-07-26',28000.00,1000000.00,35,'Annually','ACTIVE','Shiela May Manuel','Mother'),('A44870PF','BZKFC71622','24-77691','Ginto Plan','2024-11-17','2071-06-02',270000.00,3000000.00,10,'Annually','ACTIVE','Aila Roshiele C. Donayre','Daughter'),('A56049KE','OINRZ46347','24-69992','Ginto Plan','2024-11-17','2093-06-06',270000.00,3000000.00,10,'Annually','ACTIVE','Thalia D. Manuel','Daughter'),('A88522HN','CSOSK16700',NULL,'Pilak Plan','2024-12-15','2100-08-01',96000.00,1000000.00,10,'Annually','ACTIVE','Ten Lee','Brother'),('A99863DI','RHOLC27667','24-77691','Ginto Plan','2024-11-19','2091-07-21',142500.00,3000000.00,20,'Annually','ACTIVE','Rosemarie C. Donayre','Mother');
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

-- Dump completed on 2024-12-16 18:08:14
