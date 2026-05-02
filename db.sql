-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: dessert_shop
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户中文名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `token_version` int DEFAULT '0' COMMENT 'Token版本号，修改密码时递增使旧token失效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin','123456','管理员','2026-04-07 10:45:33','2026-04-09 20:32:19',0),(2,'test','123456','测试用户','2026-04-07 10:45:33','2026-04-09 20:32:19',0),(3,'guest','123456','xatu','2026-04-09 20:29:20','2026-04-09 20:29:20',0),(4,'zzz','123456','哈哈','2026-04-11 15:02:50','2026-04-11 15:02:50',0),(5,'aaa','123456','大圣','2026-04-11 15:02:50','2026-04-29 19:13:26',1),(6,'qqq','123456','猪八戒','2026-04-30 08:31:10','2026-04-30 08:31:10',0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类描述',
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类图标',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'蛋糕','各种口味的精美蛋糕','/uploads/37c03eb1-b30f-42c2-ad70-37af120e0816.jpg','2026-04-07 10:45:33','2026-04-16 19:25:59'),(2,'面包','新鲜出炉的面包甜点','/uploads/b3640bb0-10c8-40f7-91cf-da3150f128f7.jpg','2026-04-07 10:45:33','2026-04-16 19:26:03'),(3,'甜点','精致的小甜点','/uploads/85ed114f-a546-46c9-a627-f0454140e35a.jpg','2026-04-07 10:45:33','2026-04-16 19:26:05'),(4,'饮品','清爽饮品系列','/uploads/227ed0d3-2b92-4669-a739-1ec475ac7c09.jpg','2026-04-07 10:45:33','2026-04-16 19:26:08'),(8,'甜点1类','~~~~~','/uploads/3deb99cb-0ce9-4942-ab4d-3b1a6263febf.jpg','2026-04-21 11:47:25','2026-04-21 11:47:25'),(9,'分类1','···','/uploads/0a2fc6b4-3b72-4ff2-b0d6-79a414d9544e.jpg','2026-04-28 10:24:25','2026-04-28 18:36:36');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `category_id` bigint DEFAULT NULL COMMENT '分类ID',
  `stock` int DEFAULT '0' COMMENT '库存数量',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '商品描述',
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'草莓蛋糕',68.00,1,50,'新鲜草莓制作的精美蛋糕，口感香甜','/uploads/8dcf7cce-b322-4ea9-8b7a-a4457a6451c0.jpg','2026-04-07 10:45:33','2026-04-28 13:56:40'),(2,'巧克力慕斯',58.00,1,30,'浓郁巧克力慕斯，丝滑口感','/uploads/408fb695-a22e-4c0f-bb27-b98261e09d16.jpg','2026-04-07 10:45:33','2026-04-28 13:56:56'),(3,'牛角面包',12.00,2,100,'经典法式牛角面包，酥脆可口','/uploads/5357b9ec-d8ee-4d81-ba1d-4aa64fc3e1f6.jpg','2026-04-07 10:45:33','2026-04-28 13:57:06'),(4,'提拉米苏',45.00,3,40,'意大利经典甜点','/uploads/d0877ea7-a5bc-4a03-9962-d713c283a271.jpg','2026-04-07 10:45:33','2026-04-28 13:58:01'),(5,'芒果布丁',18.00,3,80,'新鲜芒果制作，口感顺滑','/uploads/19c0d944-3b86-4905-935d-46e333818247.jpg','2026-04-07 10:45:33','2026-04-28 13:58:10'),(6,'珍珠奶茶',15.00,4,200,'台式珍珠奶茶','/uploads/e2ca4a64-00a5-44f0-828f-b9f1a3a2c00a.jpg','2026-04-07 10:45:33','2026-04-28 13:58:18');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-30  8:38:44
