-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: mydatabase
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL COMMENT '聯繫方式',
  `email` varchar(255) DEFAULT NULL COMMENT '信箱',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `password` varchar(100) DEFAULT NULL,
  `status` tinyint DEFAULT '1' COMMENT '狀態(1:不禁用 0:禁用)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (8,'test','0987773652','test2@gmail.com','2024-03-26 22:31:46','2024-05-04 14:19:26','e29102a7d08fcde4b3b9629456d7b4dc',1),(21,'tt','0987776356','ff@gmail.com','2024-06-09 10:42:43','2024-06-28 22:45:02','755f6932dfccd4f951e5a8eada120522',1),(22,'uuu','0987773738','','2024-06-28 22:36:11','2024-06-28 22:44:59','e29102a7d08fcde4b3b9629456d7b4dc',1),(23,'test1',NULL,NULL,'2024-06-29 23:16:00','2024-06-29 23:16:00','123',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名稱',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `publish_date` varchar(255) DEFAULT NULL COMMENT '出版日期',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `publisher` varchar(255) DEFAULT NULL COMMENT '出版社',
  `category` varchar(255) DEFAULT NULL COMMENT '分類',
  `book_no` varchar(255) DEFAULT NULL COMMENT '圖書編號',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `updatetime` datetime DEFAULT NULL COMMENT '更新時間',
  `score` int DEFAULT NULL COMMENT '積分',
  `nums` int NOT NULL COMMENT '數量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `book_no` (`book_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'環境工程 (第6版)','本書主要是針對國內大學院校所開設的環境工程相關課程之定位及走向，進行完整的分析與歸納，並收集國內外最新資料編撰而成。書中文字力求簡明流暢，內容力求務實易懂，使同學在學習過程中，迅速吸收書中精華，進而成為最有前景的環工人。','2020-02-01','黃政賢',' 高立圖書有限公司','','ISBN13 9789863782452','http://localhost:9091/api/book/file/download/1714804961083?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2IiwiZXhwIjoxNzE2MTAwOTYxfQ.AdDJaaiib_o6L9tPXUnCBl7BEwSmXr-Mn09Qzc3PSQQ&play=1','2024-05-04 14:42:47',NULL,10,10),(2,'水汙染防治規劃原理與方法','本書歸納和整理了近30年或更長一段時期里國內外在水污染防治規劃方面的理論和技術，提出了以“一個中心、兩個基本點”為核心的水污染防治規劃的基本方法學和技術路線，并對水污染防治的適用技術進行了評述和推介。\n本書將水污染防治規劃的內容歸納為八章，較全面地論述了規劃的原理、方','2010-06-10','程聲通','化學工業出版社','air>air1','ISBN13：9787122080219','http://localhost:9091/api/book/file/download/1714839206805?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.vg1wpW_Sx_4M8Q0nzLao5AOZhCX5ccTyRKoC6wNtQwE&play=1','2024-05-05 00:04:59','2024-06-11 00:00:00',10,4),(3,'test',NULL,NULL,NULL,NULL,'','is837982','http://localhost:9091/api/book/file/download/1718546902026?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.vg1wpW_Sx_4M8Q0nzLao5AOZhCX5ccTyRKoC6wNtQwE&play=1','2024-06-16 22:09:59',NULL,10,2),(4,'test',NULL,NULL,NULL,NULL,'','isnd03940-','','2024-06-25 22:41:32',NULL,10,3),(5,'test2',NULL,NULL,NULL,NULL,'','23328932','','2024-06-25 22:42:36',NULL,10,2),(6,'test4',NULL,NULL,NULL,NULL,'','343234','','2024-06-25 22:46:28',NULL,10,3),(7,'8793',NULL,NULL,'34','3','','34234','','2024-06-25 22:48:11',NULL,10,3),(8,'',NULL,NULL,NULL,NULL,'','3982987','','2024-06-25 22:50:50','2024-06-26 00:00:00',10,3);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) DEFAULT NULL COMMENT '圖書名稱',
  `book_no` varchar(255) DEFAULT NULL COMMENT '圖書編號',
  `user_no` varchar(100) DEFAULT NULL COMMENT '用戶會員碼',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用戶名稱',
  `user_phone` varchar(255) DEFAULT NULL COMMENT '用戶手機',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `updatetime` datetime DEFAULT NULL COMMENT '更新時間',
  `score` int DEFAULT NULL COMMENT '借書積分',
  `status` varchar(255) DEFAULT '已借出' COMMENT '借書狀態',
  `days` int DEFAULT '1' COMMENT '借書天數',
  `return_date` datetime DEFAULT NULL COMMENT '歸還日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow`
--

LOCK TABLES `borrow` WRITE;
/*!40000 ALTER TABLE `borrow` DISABLE KEYS */;
INSERT INTO `borrow` VALUES (1,'環境工程 (第6版)','ISBN13 9789863782452','202405042103701469','Bob','0987776373','2024-05-04 14:45:10',NULL,10,'已歸還',1,'2024-05-05 00:00:00'),(2,'環境工程 (第6版)','ISBN13 9789863782452','202405042103701469','Bob','0987776373','2024-05-04 22:11:59',NULL,10,'已歸還',1,'2024-05-05 00:00:00'),(3,'水汙染防治規劃原理與方法','ISBN13：9787122080219','20240613723312104','yamy','0987773635','2024-06-15 16:11:12',NULL,20,'已歸還',2,'2024-06-17 00:00:00'),(4,'test','is837982','202406231376054362','er','0965554356','2024-06-23 17:20:36',NULL,10,'已歸還',1,'2024-06-24 00:00:00'),(5,'水汙染防治規劃原理與方法','ISBN13：9787122080219','202406231376054362','er','0965554356','2024-06-25 22:26:31',NULL,20,'已借出',2,'2024-06-27 00:00:00');
/*!40000 ALTER TABLE `borrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名稱',
  `remark` varchar(255) DEFAULT NULL COMMENT '備註',
  `pid` int DEFAULT NULL COMMENT '父級id',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (2,'water1',NULL,1,'2024-06-10 03:00:13',NULL),(6,'air1',NULL,3,'2024-06-10 07:33:56',NULL),(7,'water2',NULL,1,'2024-06-10 07:34:44',NULL),(9,'water',NULL,NULL,'2024-06-26 14:29:19','2024-06-27 16:00:00');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retur`
--

DROP TABLE IF EXISTS `retur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) DEFAULT NULL COMMENT '圖書名稱',
  `book_no` varchar(255) DEFAULT NULL COMMENT '圖書編號',
  `user_no` varchar(100) DEFAULT NULL COMMENT '用戶編號',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用戶會員碼',
  `user_phone` varchar(255) DEFAULT NULL COMMENT '用戶手機',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `updatetime` datetime DEFAULT NULL COMMENT '更新時間',
  `score` int DEFAULT NULL COMMENT '借書積分',
  `status` varchar(255) DEFAULT NULL COMMENT '借書狀態',
  `days` int DEFAULT NULL COMMENT '借書天數',
  `return_date` datetime DEFAULT NULL COMMENT '歸還日期',
  `real_date` datetime DEFAULT NULL COMMENT '實際歸還日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retur`
--

LOCK TABLES `retur` WRITE;
/*!40000 ALTER TABLE `retur` DISABLE KEYS */;
INSERT INTO `retur` VALUES (1,'環境工程 (第6版)','ISBN13 9789863782452','202405042103701469','Bob','0987776373','2024-05-04 22:07:05',NULL,10,'已歸還',1,'2024-05-05 00:00:00','2024-05-04 00:00:00'),(2,'環境工程 (第6版)','ISBN13 9789863782452','202405042103701469','Bob','0987776373','2024-06-09 10:53:49',NULL,10,'已歸還',1,'2024-05-05 00:00:00','2024-06-09 00:00:00'),(4,'test','is837982','202406231376054362','er','0965554356','2024-06-23 17:20:40',NULL,10,'已歸還',1,'2024-06-24 00:00:00','2024-06-23 00:00:00');
/*!40000 ALTER TABLE `retur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '會員碼',
  `age` int DEFAULT NULL COMMENT '年齡',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性別',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '聯繫方式',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `account` int DEFAULT '0' COMMENT '帳戶餘額',
  `status` tinyint DEFAULT '1' COMMENT '狀態(1:不禁用 0:禁用)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Bob','202405042103701469',32,'男','0987776373','Taichung','2024-05-04 14:44:10','2024-05-04 14:44:10',-260,0),(2,'John','202406011887910474',34,'男','0963773893',NULL,'2024-06-01 15:27:42','2024-06-01 15:27:42',0,1),(3,'HA','202406011497814331',12,'男','0973338726',NULL,'2024-06-01 15:27:59','2024-06-01 15:27:59',0,1),(4,'Betty','20240601802987275',45,'女','0986662763',NULL,'2024-06-01 15:28:13','2024-06-01 15:28:13',0,1),(5,'Frank','20240601454305702',23,'女','0987773653','Taichung','2024-06-01 15:29:26','2024-06-01 15:29:26',0,1),(6,'Tommy','20240601892824222',83,'女','0927773645','Taipei','2024-06-01 15:30:01','2024-06-01 15:30:01',0,1),(7,'Jill','202406012100716310',33,'女','0987772652',NULL,'2024-06-01 15:30:17','2024-06-01 15:30:17',0,1),(8,'Rubby','20240601174094907',53,'女','0935556276',NULL,'2024-06-01 15:30:32','2024-06-01 15:30:32',0,1),(9,'Emmy','20240601271734647',12,'女','0982553647',NULL,'2024-06-01 15:30:47','2024-06-01 15:30:47',0,1),(10,'Amy','202406011532039325',34,'女','0972663547',NULL,'2024-06-01 15:31:05','2024-06-01 15:31:05',0,1),(11,'Kevin','2024060142517869',38,'男','0983773654',NULL,'2024-06-01 15:31:43','2024-06-01 15:31:43',0,1),(12,'Ivy','202406021667139374',65,'女','0983773653',NULL,'2024-06-02 08:31:59','2024-06-02 08:31:59',0,1),(13,'Beny','202406091534634700',34,'女','0987776356',NULL,'2024-06-09 16:30:09','2024-06-09 16:30:09',0,1),(14,'yamy','20240613723312104',38,'男','0987773635',NULL,'2024-06-13 22:27:55','2024-06-13 22:27:55',100,1),(15,'test','202406232018225860',34,'女','0945556786',NULL,'2024-06-23 15:44:55','2024-06-23 15:44:55',0,1),(16,'rt','202406231338460554',66,'女','0988888888',NULL,'2024-06-23 15:47:27','2024-06-23 15:47:27',0,1),(17,'er','202406231450704854',67,'女','0955556546',NULL,'2024-06-23 15:48:26','2024-06-23 15:48:26',0,1),(18,'er','20240623386498289',34,'女','0945553432',NULL,'2024-06-23 15:50:12','2024-06-23 15:50:12',0,1),(19,'df','20240623521193250',34,'女','0953334567',NULL,'2024-06-23 15:57:11','2024-06-23 15:57:11',0,1),(20,'df','202406231046539862',34,'女','0953334567',NULL,'2024-06-23 15:59:25','2024-06-23 15:59:25',0,1),(21,'df','202406231883298441',34,'女','0953334567',NULL,'2024-06-23 15:59:45','2024-06-23 15:59:45',0,1),(22,'f','202406231778473891',78,'女','0967775435',NULL,'2024-06-23 16:04:08','2024-06-23 16:21:35',0,1),(23,'er','202406231376054362',76,'男','0965554356',NULL,'2024-06-23 16:52:04','2024-06-23 16:52:04',34,1),(24,'uu','202406281322535261',38,'女','0983777363',NULL,'2024-06-28 22:17:33','2024-06-28 22:26:49',0,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mydatabase'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-30  7:54:53
